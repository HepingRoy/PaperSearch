package solr.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import solr.dataimport.FileConvert;
import solr.dataimport.IAttachmentManager;
@Service
public class FileUploadImpl implements FileUpload {
	// 上传文件存储目录
	@Value("#{solrURLProperties['UPLOAD_DIRECTORY']}")
	private String UPLOAD_DIRECTORY;
	@Autowired
	Timss4SolrService timss4SolrService;
	@Autowired
	IAttachmentManager attachManager;
	@Override
	public void upload(MultipartFile[] files, String site, String userid, String userName, String label) {
		// TODO Auto-generated method stub
		for (MultipartFile file : files) {
			String fileName = site + file.getOriginalFilename();
			File targetFile = new File(UPLOAD_DIRECTORY, fileName);
			if (!targetFile.exists()) {
				targetFile.setWritable(true, false);
				targetFile.mkdirs();
			}
			try {
				Map<String, Object> params = new HashMap<>();
				params.put("site", site);
				params.put("fileName", file.getOriginalFilename());
				params.put("userId", userid);
				params.put("userName", userName);
				params.put("label", label);
				params.put("url", UPLOAD_DIRECTORY+fileName);
				timss4SolrService.insert(params, file.getInputStream());
				file.transferTo(targetFile);
				//attachManager.file2img(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
