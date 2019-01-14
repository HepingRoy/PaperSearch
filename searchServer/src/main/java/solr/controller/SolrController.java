package solr.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import solr.model.QResult;
import solr.model.RPic;
import solr.model.SolrPage;
import solr.service.FileUpload;
import solr.service.Timss4SolrService;

/**
 * @author 890213
 * @version 1.0
 * @date 2017/8/11 15:34
 */
@Controller
@RequestMapping("/solr")
public class SolrController {
	@Autowired
	Timss4SolrService timss4SolrService;
	@Autowired
	FileUpload fileUpload;
	@Value("#{solrURLProperties['picPath']}")
	private String picPath;
	@RequestMapping("/query.do")
	public @ResponseBody QResult query(@RequestBody SolrPage page) {
		if (page == null)
			return null;
		String keyword = page.getParams().get("keyword");
		return timss4SolrService.query(keyword, null, null, 0, 50);
	}

	@RequestMapping("/upload.do")
	public void fileUpload(@RequestParam(value = "file", required = false) MultipartFile[] files,
			HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String userid = multipartRequest.getParameterValues("userid") != null
				? multipartRequest.getParameterValues("userid")[0] : null;
		String authors = multipartRequest.getParameterValues("authors") != null
				? multipartRequest.getParameterValues("authors")[0] : null;
		String year = multipartRequest.getParameterValues("year") != null
				? multipartRequest.getParameterValues("year")[0] : null;
		String name = multipartRequest.getParameterValues("name") != null
				? multipartRequest.getParameterValues("name")[0] : null;
		String isPrivate = multipartRequest.getParameterValues("isprivate") != null
				? multipartRequest.getParameterValues("isprivate")[0] : null;
        fileUpload.upload(files, userid, authors, year, name, isPrivate);
	}
	@RequestMapping("/download.do")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException{
	        BufferedInputStream bis = null; 
	        BufferedOutputStream bos = null; 
	        //获取下载文件
	        String fileId = URLDecoder.decode(request.getParameter("fileId"),"UTF-8");
	        File file = new File(fileId);
	        if(!file.exists())return;
	        //MultipartFile multfile = new MultipartFile()
	        //获取文件的长度
	        long fileLength = file.length(); 
	        //设置文件输出类型
	        //response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
	        response.setContentType("application/x-download;charset=UTF-8");
	        response.setCharacterEncoding("UTF-8");
	        response.setHeader("Content-disposition", "attachment; filename=" 
	                + new String((fileId.split("/")[(fileId.split("/")).length-1]).getBytes("utf-8"),"iso-8859-1"));
	        //设置输出长度
	        response.setHeader("Content-Length", String.valueOf(fileLength)); 
	        //获取输入流
	        bis = new BufferedInputStream(new FileInputStream(fileId)); 
	        //输出流
	        bos = new BufferedOutputStream(response.getOutputStream()); 
	        byte[] buff = new byte[2048]; 
	        int bytesRead; 
	        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) { 
	            bos.write(buff, 0, bytesRead); 
	        } 
	        //关闭流
	        bis.close(); 
	        bos.close();
	}
	@RequestMapping("/getpic.do")
	public @ResponseBody RPic test(@RequestBody Map<String,String> param){
		RPic result = new RPic();
		if(param==null){
			result.setCode(RPic.FAILURE);
			return result;
		}
		String fileName=param.get("fileName");
		File file = new File(picPath+fileName);
		if(!file.exists()){
			result.setCode(RPic.FAILURE);
			return result;
		}
		int total = file.list().length;
		List<Map<String, String>> list = new ArrayList<>();
		for(int i=1;i<=total;i++){
			Map<String, String> map = new HashMap<>();
			map.put("href", "../pic/"+fileName+"/"+i+".png");
			map.put("title", fileName+" "+i+"/"+total);
			list.add(map);
		}
		result.setCode(RPic.SUCCESS);
		result.setPic(list);
		return result;
	}
	
}
