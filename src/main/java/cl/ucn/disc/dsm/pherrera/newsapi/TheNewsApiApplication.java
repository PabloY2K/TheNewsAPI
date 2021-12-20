package cl.ucn.disc.dsm.pherrera.newsapi;

import cl.ucn.disc.dsm.pherrera.newsapi.model.News;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;


@SpringBootApplication
public class TheNewsApiApplication {
	@Autowired
	private NewsRepository newsRepository;

	/**
	 * The Main starting point
	 * @param args to use
	 */



	public static void main(String[] args) {
		SpringApplication.run(TheNewsApiApplication.class, args);
	}
	protected InitializingBean initializingDatabase(){
		return () --> {
		final new News(
				News news = new News(
				"Titulo de Noticia",
				"Fuente de la noticia",
				"Autor de la noticia",
				"URL de la noticia",
				"El URL de la imagen de la noticia",
				"La descripcion de la noticia",
				"El contenido de la noticia",
				ZonedDateTime.now(ZoneId.of("-4j"))
		);
				this.newsRepository.save(news);
		};
	}
//
}
