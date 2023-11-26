package com.example.muzfi.Services.Post;

import com.example.muzfi.Dto.PostDto.ListingCreateDto;
import com.example.muzfi.Dto.PostDto.ListingDetailsDto;
import com.example.muzfi.Dto.PostDto.ListingUpdateDto;
import com.example.muzfi.Dto.PostDto.PostDetailsDto;

import java.util.List;
import java.util.Optional;

public interface ListingService {
    Optional<List<ListingDetailsDto>> getAllListings();

    Optional<ListingDetailsDto> getListingById(String listingId);

    Optional<?> createListing(ListingCreateDto listingDto);

    Optional<PostDetailsDto> updateListing(ListingUpdateDto updateDto);
}
