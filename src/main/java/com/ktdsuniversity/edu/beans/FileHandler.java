package com.ktdsuniversity.edu.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.exceptions.FileNotExistsException;

//@Component
public class FileHandler {

	/**
	 * 업로드된 파일이 저장될 위치
	 */
	private String baseDir;
	
	/**
	 * 파일명을 난독화 할지에 대한 여부
	 */
	private boolean enableObfuscation;
	
	/**
	 * 확장자를 숨길지에 대한 여부.
	 */
	private boolean enableObfuscationHideExt;

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public void setEnableObfuscation(boolean enableObfuscation) {
		this.enableObfuscation = enableObfuscation;
	}

	public void setEnableObfuscationHideExt(boolean enableObfuscationHideExt) {
		this.enableObfuscationHideExt = enableObfuscationHideExt;
	}
	
	/**
	 * 서버에 등록한 파일을 반환한다.
	 * @param fileName 찾아올 파일 명
	 * @return 파일 객체
	 */
	public File getStoredFile(String fileName) {
		return new File(baseDir, fileName);
	}
	
	/**
	 * 파일 다운로드를 처리한다.
	 * @param downloadFile 다운로드 시킬 서버의 파일 객체.
	 * @param downloadFileName 브라우저에게 전달할 파일의 이름.
	 * @return 다운로드 리소스.
	 */
	public ResponseEntity<Resource> getResponseEntity(
			File downloadFile,
			String downloadFileName) {
		// HTTP 응답의 Header 정보를 생성한다.
		HttpHeaders header = new HttpHeaders();
		// HTTP 응답 Header에 파일을 첨부하여 보내도록 설정한다. 
		header.add(HttpHeaders.CONTENT_DISPOSITION, 
				"attachment; filename=" + downloadFileName);
		
		// 다운로드 할 파일의 리소스(Byte)를 생성한다.
		InputStreamResource resource;
		try {
			resource = new InputStreamResource(new FileInputStream(downloadFile));
		} catch (FileNotFoundException e) {
			throw new FileNotExistsException("파일이 존재하지 않습니다.");
		}
		
		// HTTP 응답 객체를 직접 생성한다.
		return ResponseEntity.ok() // Status = 200
					.headers(header) // HTTP Header 설정
					.contentLength(downloadFile.length()) // 다운로드할 파일의 크기
					// 다운로드 할 파일의 타입
					// application/octet-stream: 모든 파일
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					// 다운로드 할 파일의 Resource(Byte)
					.body(resource);
		
		
	}
	
	/**
	 * 사용자가 업로드한 파일을 서버에 저장시킨다.
	 * @param multipartFile 사용자가 업로드한 파일
	 * @return 업로드 결과
	 */
	public StoredFile storeFile(MultipartFile multipartFile) {
		
		// 사용자가 파일을 업로드 하지 않았다면 null을 반환한다.
		if (multipartFile == null || multipartFile.isEmpty()) {
			return null;
		}
		
		// 사용자가 업로드한 파일의 이름.
		String originalFileName = multipartFile.getOriginalFilename();
		
		// 사용자가 업로드한 파일의 이름을 난독화 설정에 따라 받아온다.
		String fileName = getObfuscationFileName(originalFileName);
		
		// 파일이 저장될 위치를 지정한다.
		File storePath = new File(baseDir, fileName);
		
		// 만약, 파일이 저장될 위치(폴더)가 존재하지 않는다면
		if (!storePath.getParentFile().exists()) {
			// 폴더를 생성해준다.
			storePath.getParentFile().mkdirs();
		}
		
		// 사용자가 업로드한 파일을 파일이 저장될 위치로 이동시킨다.
		try {
			multipartFile.transferTo(storePath);
		} catch (IllegalStateException | IOException e) {
			// 업로드한 파일을 이동하는 중에 예외가 발생하면
			// 업로드를 실패한 것이므로 null을 반환한다.
			return null;
		}
		// 업로드 결과를 반환한다.
		return new StoredFile(originalFileName, storePath);
	}
	
	/**
	 * 파일명을 난독화 처리하는 기능.
	 * @param fileName 사용자가 업로드한 파일의 이름.
	 * @return 설정값에 따라, 난독화된 이름 또는 업로드 한 파일의 이름.
	 */
	private String getObfuscationFileName(String fileName) {
		
		// 난독화 설정을 했을 때
		if (enableObfuscation) {
			// 파일명에서 확장자를 분리한다.
			String ext = fileName.substring( fileName.lastIndexOf(".") );
			// 현재시간을 기준으로 난독화된 코드를 받아온다.
			String obfuscationName = UUID.randomUUID().toString();
			// 확장자를 숨김처리 설정을 했다면
			if (enableObfuscationHideExt) {
				// 확장자를 제외한 난독화된 코드만 반환하고
				return obfuscationName;
			}
			// 확장자를 숨김처리 하지 않았다면
			// 난독화된 코드뒤에 확장자를 붙여서 반환한다.
			return obfuscationName + ext;
		}
		
		return fileName;
	}
	
	// Class 안의 Class => Nested Class
	public class StoredFile {
		/**
		 * 사용자가 업로드한 파일의 이름 (확장자 포함)
		 */
		private String fileName;
		
		/**
		 * 서버에 저장된 파일의 실제 이름.
		 * 난독화 설정했다면, 파일의 이름은 난독화되어 저장된다.
		 */
		private String realFileName;
		
		/**
		 * 서버에 저장된 파일의 경로.
		 */
		private String realFilePath;
		
		/**
		 * 서버에 저장된 파일의 크기. (byte 단위)
		 */
		private long fileSize;
		
		/**
		 * 업로드 한 파일의 정보를 셋팅한다.
		 * @param fileName 사용자가 업로드한 파일의 이름.
		 * @param storeFile 서버에 저장된 파일 객체.
		 */
		public StoredFile(String fileName, File storeFile) {
			this.fileName = fileName;
			this.realFileName = storeFile.getName();
			this.realFilePath = storeFile.getAbsolutePath();
			this.fileSize = storeFile.length();
		}

		public String getFileName() {
			return fileName;
		}

		public String getRealFileName() {
			return realFileName;
		}

		public String getRealFilePath() {
			return realFilePath;
		}

		public long getFileSize() {
			return fileSize;
		}
		
	}
	
}







