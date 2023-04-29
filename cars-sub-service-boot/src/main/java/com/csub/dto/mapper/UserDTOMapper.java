package com.csub.dto.mapper;

import com.csub.dto.UserDTO;
import com.csub.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        int subscriptionId = user.getSubscription() == null ? 0 : user.getSubscription().getId();
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhone(),
                user.isBlocked(),
                subscriptionId);
    }
}
