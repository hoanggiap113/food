package com.food.model.context;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;

@Setter
@Getter
public class CartContext {
    private final Long userId;
    private final UUID sessionId;

    public CartContext(Long userId, UUID sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public Optional<Long> getUserId() {
        return Optional.ofNullable(userId);
    }

    public Optional<UUID> getSessionId() {
        return Optional.ofNullable(sessionId);
    }

    public boolean isGuest() {
        return userId == null;
    }

    public boolean isAuthenticatedUser() {
        return userId != null;
    }

    public String getIdentifier() {
        return isAuthenticatedUser() ? "User: " + userId : "Guest: " + sessionId;
    }
}
