package io.javabrains.moviecatalogservice3.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.javabrains.moviecatalogservice3.models.CatalogItem;
import io.javabrains.moviecatalogservice3.models.Movie;
import io.javabrains.moviecatalogservice3.models.Rating;
import io.javabrains.moviecatalogservice3.models.UserRating;



@RestController
@RequestMapping("/catalog")
public class MovieCatalogResourceController {
	@Autowired
	private RestTemplate restTemplate;//
	/*@Autowired
	private WebClient.Builder webClientBuilder;*/
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		
	
		
		//get all rated movie Id (lets assume this is the data that we got from the ratings-data-service)
		UserRating ratings=restTemplate.getForObject("http://ratings-data-microservice/ratingsdata/users/"+userId, UserRating.class);
		
		
		
		return ratings.getUserRating().stream().map( rating->
		{	//for each movie id call the movie info service  and get details
			Movie movie=restTemplate.getForObject("http://movie-info-microservice/movies/"+rating.getMovieId(), Movie.class);
			
			/*Movie movie =webClientBuilder.build()
					.get()
					.uri("http://localhost:8082/movies/"+rating.getMovieId())
					.retrieve()
					.bodyToMono(Movie.class)
					.block();
			
			*/
			//put them all together
			return new CatalogItem(movie.getName(),movie.getDescription(),rating.getRating());
			
		}).collect(Collectors.toList());
		
		
//		List<CatalogItem> CatalogItemList=new ArrayList<CatalogItem>();
//
//		for(Rating rating:ratings) {
//			Movie movie=restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
//			
//			CatalogItemList.add(new CatalogItem(movie.getName(),"ScienceFic",rating.getRating()));
//		}
//		
//	
//	
//
//		return CatalogItemList;
		
	}

}
