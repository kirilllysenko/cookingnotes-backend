package com.kirill.cookingnotes.profile.converter;

import com.kirill.cookingnotes.profile.dto.MeUserResponseDTO;
import com.kirill.cookingnotes.profile.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToMeResponseConverter implements Converter<User, MeUserResponseDTO> {

    @Override
    public MeUserResponseDTO convert(User user){
        return MeUserResponseDTO
                .builder()
                .id(user.getId())
                .avatar(user.getAvatar())
                .name(user.getDisplayName())
                .build();
    }
}
