package solr.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import solr.model.QResult;
import solr.model.SolrPage;
import solr.dataimport.FileConvertFactory;
import solr.solrj.SolrCilentService;
import java.io.InputStream;
import java.util.*;

/**
 * @author 890213
 * @version 1.0
 * @date 2017/8/10 16:08
 */

@Service
public class Timss4SolrServiceImpl implements Timss4SolrService {

	@Autowired
	SolrCilentService solrCilentService;
	@Value("#{solrURLProperties['core']}")
	private String core;

	@Override
	public QResult query(String keyWord, String site, String type, int start, int rows) {
		String split = null;
		String queryFeild = null;
		String queryFilter = null;
		String queryKey = keyWord;
		if ((split = analyseKeyWords(keyWord)) != null) {
			String[] keys = keyWord.split(split);
			queryKey = keys[1];
			queryFilter = "year:*" + keys[0].replaceAll(" ", "").replaceAll("　", "") + "*";
			queryFeild = "paper_keyword:"+ keys[1].replaceAll(" ", "").replaceAll("　", "");
		} else {
			queryFeild = "paper_keyword:" + keyWord;
		}
		SolrPage params = new SolrPage();
		params.setStart(start);
		params.setRows(rows);
		Map<String, String> fields = new HashMap<String, String>();
		Map<String, String> param = new HashMap<String, String>();
		if (site != null) {
			fields.put("site", site);
		}
		if (type != null) {
			fields.put("file_type", type);
		}
		param.put("hl", "true");
		//param.put("fl", "content,author");
		params.setParams(param);
		params.setFields(fields);
		QResult result = new QResult();
		try {
			QueryResponse response = solrCilentService.query(core, queryFeild, queryFilter, params);
			Map<String, Map<String, List<String>>> hl = response.getHighlighting();
			SolrDocumentList documentList = response.getResults();
			List<QResult.Doc> docList = new ArrayList<QResult.Doc>();
			//DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			for (int i = 0; i < documentList.size(); i++) {
				QResult.Doc doc = result.new Doc();
				String fileName = null;
				if (hl.get(documentList.get(i).get("id")) != null) {
					fileName = hl.get(documentList.get(i).get("id")).get("name") != null
							? Highlight(hl.get(documentList.get(i).get("id")).get("name").get(0),queryKey) : null;

				}
				String content = null;
				if (hl.get(documentList.get(i).get("id")) != null) {
					content = hl.get(documentList.get(i).get("id")).get("content") != null
							? Highlight(hl.get(documentList.get(i).get("id")).get("content").get(0),queryKey) : null;
				}
				if(fileName==null&&content==null)continue;
				if(fileName==null){
					fileName=documentList.get(i).get("name").toString();
				}
				doc.setTitle(fileName.substring(1,fileName.length()-5));
				doc.setContent(content);
				doc.setAuthor(
						documentList.get(i).get("authors") != null ? documentList.get(i).get("authors").toString() : null);
				doc.setSite(documentList.get(i).get("author") != null
						? documentList.get(i).get("authors").toString() : null);
				doc.setLabel(documentList.get(i).get("year") != null
						? documentList.get(i).get("year").toString() : null);
				doc.setSize(documentList.get(i).get("file_size") != null
						? documentList.get(i).get("file_size").toString() : null);
				doc.setUrl(documentList.get(i).get("file_url") != null ? documentList.get(i).get("file_url").toString()
						: null);
				docList.add(doc);
			}
			result.setRows(docList);
			result.setTotal(docList.size());
			result.setCode(QResult.SUCCESS);
		} catch (Exception e) {
			result.setCode(QResult.FAILURE);
			result.setMsg("搜索引擎故障");
			e.printStackTrace();
		}
		return result;
	}

	public String Highlight(String content, String key) {
		String[] keys = null;
		content = content.replaceAll("<em>", "").replaceAll("</em>", "");
		if (key.contains(" ")) {
			keys = key.split(" ");
		} else if (key.contains("　")) {
			keys = key.split("　");
		}
		if (keys != null) {
			for (int i = 0; i < keys.length; i++) {
				if (StringUtils.isEmpty(keys[i].replaceAll(" ", "").replaceAll("　", "")))
					continue;
				content = analyseResult(content, keys[i]);
			}
		} else {
			content = analyseResult(content, key);
		}
		if(content.contains("<em>")){
			return content;
		}else return null;
		

	}

	public String analyseResult(String content, String key) {
		StringBuffer result = new StringBuffer();
		int len = key.length();
		int start = 0;
		int end = 0;
		while ((end = content.indexOf(key, start)) != -1) {
			result.append(content.substring(start, end));
			result.append("<em>").append(key).append("</em>");
			start = end + len;
		}
		if (start > 0 && start < content.length()) {
			result.append(content.substring(start, content.length()));
		}
		if (start != 0) {
			return result.toString();
		} else
			return content;
	}

	public String analyseKeyWords(String key) {
		if (StringUtils.isEmpty(key))
			return null;
		String[] param = new String[] { ":", "：" };
		for (int i = 0; i < param.length; i++) {
			if (key.contains(param[i])) {
				return param[i];
			}
		}
		return null;
	}

	@Override
	public QResult insert(Map<String, Object> params, InputStream fileStream) {
		QResult result= new QResult();
		SolrInputDocument doc = new SolrInputDocument();
		if(params.get("userid")==null){
			result.setCode(QResult.FAILURE);
			result.setMsg("缺少用户信息");
			return result;
		}
		FileConvertFactory fileConvertFactory = new FileConvertFactory();
		String content = fileConvertFactory.fileCovertText(fileStream, params.get("fileName").toString());
		doc.addField("id", UUID.randomUUID().toString());
		doc.addField("content", content.replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", ""));
		doc.addField("name", params.get("name"));
		doc.addField("authors", params.get("authors"));
		doc.addField("content_type", params.get("content_type"));
		doc.addField("userid", params.get("userid"));
		doc.addField("file_url", params.get("url"));
		doc.addField("year", params.get("year"));
		doc.addField("isprivate", params.get("isprivate"));
		try {
			solrCilentService.addIndex(core, doc);
			result.setCode(QResult.SUCCESS);
			result.setMsg("索引操作成功");
		} catch (Exception e) {
			result.setCode(QResult.FAILURE);
			result.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public QResult update(Map<String, Object> params, InputStream fileStream) {
		return null;
	}

	@Override
	public QResult delete(String fileName, String site, String userId) {
		return null;
	}

	/*public static void main(String[] args) {
		Timss4SolrServiceImpl tt = new Timss4SolrServiceImpl();
		String test = "字符s位置如果 startindex 是负数，则 startindex 被当作零。如果它比最大的字符位s置索引还大，则它被当作最大的可能索引。字符位s置";
		System.out.println(tt.Highlight(test, "字符s位置"));
		System.out.println(tt.analyseResult(tt.Highlight(test, "字符s位置"), " 字m符 "));
	}*/
}
