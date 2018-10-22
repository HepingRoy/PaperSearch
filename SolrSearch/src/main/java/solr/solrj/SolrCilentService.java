package solr.solrj;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import solr.bean.SolrPage;
import java.util.Collection;
import java.util.List;

/**
 * @author 890213
 * @version 1.0
 * @date 2017/8/10 10:00
 */

public interface SolrCilentService {
    void addIndexs(String core, Collection<SolrInputDocument> documents) throws Exception;
    void addIndex(String core, SolrInputDocument document) throws Exception;
    void deleteIndexs(String core, List<String> indexIds) throws Exception;
    void deleteIndex(String core, String indexId) throws Exception;
    QueryResponse query(String core, String q, String fq, SolrPage params) throws Exception;

}
