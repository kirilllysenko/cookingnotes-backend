package com.kirill.cookingnotes.recipe.converter;

import com.kirill.cookingnotes.recipe.dto.CommentResponseDTO;
import com.kirill.cookingnotes.recipe.entity.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommentToResponseConverter implements Converter<Comment, CommentResponseDTO> {

    @Override
    public CommentResponseDTO convert(Comment comment){
        return CommentResponseDTO
                .builder()
                .id(comment.getId())
                .userAvatar(comment.getUser().getAvatar())
                .date(comment.getDate())
                .userName(comment.getUser().getDisplayName())
                .rating(comment.getRating())
                .text(comment.getText())
                .userId(comment.getUser().getId())
                .build();
    }
}
