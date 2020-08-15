package io.javabrains.ratingsdataservice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.ratingsdataservice.models.Rating;
import io.javabrains.ratingsdataservice.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataResourceController {
	
	@RequestMapping("/{movieId}")//this method takes in a movie id and returns the rating for that
	public Rating getRating(@PathVariable String movieId ) {
		return new Rating(movieId,4);
	}
	
	@RequestMapping("/users/{userId}")//this method takes in a user id and returns the movieid & rating for that
	public UserRating getRatingAndMovieId(@PathVariable("userId") String userId ) {
	
		
		List<Rating> ratings= Arrays.asList(
				new Rating("100",4),
				new Rating("200",5)
		);
		
		UserRating userRating=new UserRating();
		userRating.setUserRating(ratings);
		return userRating;
	}
	
	

}
