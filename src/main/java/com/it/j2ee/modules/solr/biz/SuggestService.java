package com.it.j2ee.modules.solr.biz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Collation;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Correction;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.params.ModifiableSolrParams;

import com.alibaba.fastjson.JSONObject;
import com.it.j2ee.modules.solr.SolrServerFactory;

public class SuggestService {
	
	private static HttpSolrServer solr = SolrServerFactory.getInstance();

	@SuppressWarnings("deprecation")
	public static String suggest(String orgiWord) {
		ModifiableSolrParams params = new SolrQuery();
		params.set("qt", "/new_core/suggest");
		params.set("q", "name:" + orgiWord);
		params.set("spellcheck", "on");
        params.set("spellcheck.build", "true");
        params.set("spellcheck.onlyMorePopular", "true");

        params.set("spellcheck.count", "100");
        params.set("spellcheck.alternativeTermCount", "4");
        params.set("spellcheck.onlyMorePopular", "true");

        params.set("spellcheck.extendedResults", "true");
        params.set("spellcheck.maxResultsForSuggest", "5");

        params.set("spellcheck.collate", "true");
        params.set("spellcheck.collateExtendedResults", "true");
        params.set("spellcheck.maxCollationTries", "5");
        params.set("spellcheck.maxCollations", "3");

		QueryResponse response = null;
		try {
			response = solr.query(params);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
		List<String> suggestList = new ArrayList<String>();
		if (spellCheckResponse != null) {
			List<Suggestion> suggestionList = spellCheckResponse.getSuggestions();
            for (Suggestion suggestion : suggestionList) {
                System.out.println("Suggestions NumFound: " + suggestion.getNumFound());
                System.out.println("Token: " + suggestion.getToken());
                System.out.print("Suggested: ");
                List<String> suggestedWordList = suggestion.getAlternatives();
                for (String word : suggestedWordList) {
                	suggestList.add(word);
                    System.out.println(word + ", ");
                }
                System.out.println();
            }
            System.out.println();
            Map<String, Suggestion> suggestedMap = spellCheckResponse.getSuggestionMap();
            for (Map.Entry<String, Suggestion> entry : suggestedMap.entrySet()) {
                System.out.println("suggestionName: " + entry.getKey());
                Suggestion suggestion = entry.getValue();
                System.out.println("NumFound: " + suggestion.getNumFound());
                System.out.println("Token: " + suggestion.getToken());
                System.out.print("suggested: ");

                List<String> suggestedList = suggestion.getAlternatives();
                for (String suggestedWord : suggestedList) {
                    System.out.print(suggestedWord + ", ");
                }
                System.out.println("\n\n");
            }

            System.out.println("The First suggested word for solr is : " + spellCheckResponse.getFirstSuggestion(orgiWord));
            System.out.println("\n\n");

            List<Collation> collatedList = spellCheckResponse.getCollatedResults();
            if (collatedList != null) {
                for (Collation collation : collatedList) {
                    System.out.println("collated query String: " + collation.getCollationQueryString());
                    System.out.println("collation Num: " + collation.getNumberOfHits());
                    List<Correction> correctionList = collation.getMisspellingsAndCorrections();
                    for (Correction correction : correctionList) {
                        System.out.println("original: " + correction.getOriginal());
                        System.out.println("correction: " + correction.getCorrection());
                    }
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println("The Collated word: " + spellCheckResponse.getCollatedResult());
            System.out.println();
        }

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", suggestList);

		String json = JSONObject.toJSONString(suggestList);
		return json;

	}

	public static void main(String[] args) {
		SuggestService.suggest("m");
	}

}
