package com.cinema.booking_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cinema.booking_service.dto.request.ReserveSeatRequest;
import com.cinema.booking_service.entity.ShowSeat;
import com.cinema.booking_service.enums.SeatStatus;
import com.cinema.booking_service.repository.BookingRepository;
import com.cinema.booking_service.repository.BookingSeatRepository;
import com.cinema.booking_service.repository.ShowSeatRepository;
import com.cinema.booking_service.service.BookingService;

@SpringBootTest
class BookingServiceApplicationTests {
	@Autowired
	private BookingService bookingService;

	@Autowired
	private ShowSeatRepository showSeatRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private BookingSeatRepository bookingSeatRepository;

	@BeforeEach
	void setup() {

		bookingSeatRepository.deleteAll();

		bookingRepository.deleteAll();

		showSeatRepository.deleteAll();

		ShowSeat seat = ShowSeat.builder()
				.showtimeId(1L)
				.seatNumber("H7")
				.status(SeatStatus.AVAILABLE)
				.build();

		showSeatRepository.save(seat);

	}

	@Test
	void shouldAllowOnlyOneBooking() throws Exception {

		int users = 100;

		ExecutorService executor = Executors.newFixedThreadPool(20);
		CountDownLatch latch = new CountDownLatch(users);
		AtomicInteger success = new AtomicInteger();
		AtomicInteger fail = new AtomicInteger();

		for (int i = 0; i < users; i++) {
			long userId = i;
			executor.submit(() -> {
				try {
					ReserveSeatRequest request = new ReserveSeatRequest();
					request.setUserId(userId);
					request.setShowtimeId(1L);
					request.setSeatNumbers(List.of("H7"));
					bookingService.reserveSeat(request);
					success.incrementAndGet();
				} catch (Exception e) {
					fail.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();
		executor.shutdown();
		System.out.println("Success = " + success.get());
		System.out.println("Fail = " + fail.get());

		assertEquals(1, success.get());
		assertEquals(99, fail.get());

	}

}
