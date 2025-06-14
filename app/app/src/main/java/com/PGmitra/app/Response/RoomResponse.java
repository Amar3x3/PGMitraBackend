package com.PGmitra.app.Response;

import java.math.BigDecimal;

public record RoomResponse(Integer room_no, Long id, Integer capacity, Integer occupied, BigDecimal rent) {
}
