package com.kirill.cookingnotes.profile.converter;

import com.kirill.cookingnotes.profile.dto.PublicProfileResponseDTO;
import com.kirill.cookingnotes.profile.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToResponseConverter implements Converter<User, PublicProfileResponseDTO> {

    @Override
    public PublicProfileResponseDTO convert(User user) {
        return new PublicProfileResponseDTO(
                user.getId(),
                user.getDisplayName(),
                user.getCountry(),
                user.getCity(),
                user.getAbout(),
                user.getBlogLink(),
                user.getTwitterLink(),
                user.getFacebookLink(),
                user.getInstagramLink(),
                user.getAvatar(),
                user.getCover());
    }
}
