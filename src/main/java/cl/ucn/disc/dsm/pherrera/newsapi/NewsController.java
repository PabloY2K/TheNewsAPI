package cl.ucn.disc.dsm.pherrera.newsapi;

import cl.ucn.disc.dsm.pherrera.newsapi.model.News;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.kwabenaberko.newsapilib.network.APIClient;
import com.kwabenaberko.newsapilib.network.APIService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class NewsController {
    /**
     *
     * The Repo of News
     */
    private final NewsRepository newsRepository;

    public NewsController(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }



    @GetMapping("/v1/news")
    public List<News> all(@RequestParam(required = false, defaultValue = "false") Boolean reload){

        if(reload){
            this.reloadNewsFromNewsApi();
        }


        final List<News> theNews = this.newsRepository.findAll();
        return theNews;

    }

    private void reloadNewsFromNewsApi() {
        final String API_KEY = "9ed34a9d5e134d0cb712fc70c9e21a30"

        final APIService apiService = APIClient.getAPIService();
        final Map<String, String> reqParams = new HashMap<>();
        reqParams.put("apiKey", API_KEY);
        reqParams.put("category", "tecnology");
        reqParams.put("pageSize", String.valueOf(pageSize));
        Response<ArticleResponse> articleResponse =
                apiService.getTopHeadlines(reqParams).execute();
        if(articleResponse.isSuccessful()) {
            List<Article> articles = articlesResponse.body().getArticles();
            List<News> news = new ArrayList<>();
            for(Article article: articles){
                new.add(toNews(article));

            }
    catch (IOException){
        log.error("Getting the Articles from NewsAPI", e)
    }
    }

    private static News toNews(final Article article){

        if(article.getAuthor() == null || article.getAuthor().length() < 3){
            article.setAuthor("No Author*");
        }
        if(article.getTitle() == null || article.getTitle().length() < 3){
            article.setTitle("No Title*");
        }
        if(article.getDescription() == null || article.getDescription().length() < 4){
            article.setDescription("No Description*");
        }
        ZonedDateTime publishedAt = ZonedDateTime.parse(article.getPublishedAt())
                .withZoneSameInstant(ZoneId.of("-3"));

        return new News(
                article.getTitle(),
                article.getSource().getName(),
                article.getAuthor(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getDescription(),
                article.getDescription(),
                publishedAt
        )
        return null;
    }

    @GetMapping("/v1/news/{id}")
    public News one(@PathVariable final Long id){
        return this.newsRepository.findById(id).
                orElseThrow(()-> new RuntimeException("News Not Found :(")"));



//
    }
}
