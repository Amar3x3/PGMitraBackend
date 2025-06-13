package com.PGmitra.app.Response;

import java.math.BigDecimal;

public record RoomResponse(Long id, Integer capacity, Integer occupied, BigDecimal rent) {
}
