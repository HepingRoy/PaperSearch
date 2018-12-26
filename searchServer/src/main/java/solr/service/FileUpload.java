package solr.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUpload {
	void upload(MultipartFile[] file, String userid, String author, String year, String name, String isPrivate);
}
