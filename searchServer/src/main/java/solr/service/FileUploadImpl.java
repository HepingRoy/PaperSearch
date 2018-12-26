package solr.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import solr.dataimport.FileConvert;
@Service
public class FileUploadImpl implements FileUpload {
	// 上传文件存储目录
	@Value("#{solrURLProperties['UPLOAD_DIRECTORY']}")
	private String UPLOAD_DIRECTORY;
	@Autowired
	Timss4SolrService timss4SolrService;
	@Override
	public void upload(MultipartFile[] files, String userid, String author, String year, String name, String isPrivate) {
		// TODO Auto-generated method stub
		for (MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			File targetFile = new File(UPLOAD_DIRECTORY, fileName);
			if (!targetFile.exists()) {
				targetFile.setWritable(true, false);
				targetFile.mkdirs();
			}
			try {
				Map<String, Object> params = new HashMap<>();
				params.put("userid", userid);
				params.put("content_type", "pdf");
				params.put("author", author);
				params.put("name", name);
				params.put("year", year);
				params.put("isprivate",isPrivate);
				params.put("url", UPLOAD_DIRECTORY+fileName);
				timss4SolrService.insert(params, file.getInputStream());
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
