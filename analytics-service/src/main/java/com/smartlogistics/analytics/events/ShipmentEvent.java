package com.smartlogistics.analytics.events;

public record ShipmentEvent(String shipmentId, String status, String user, String timestamp) {}
