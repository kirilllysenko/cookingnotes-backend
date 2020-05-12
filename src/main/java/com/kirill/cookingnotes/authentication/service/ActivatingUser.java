package com.kirill.cookingnotes.authentication.service;

import com.kirill.cookingnotes.profile.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class ActivatingUser {
    private final User user;
    private final Date createdAt = new Date();
}
