package com.smartlogistics.shipment.events;

public record ShipmentEvent(String shipmentId, String status, String user, String timestamp) {}
