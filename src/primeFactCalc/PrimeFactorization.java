package primeFactCalc;

import java.util.ArrayList;
import java.util.List;

public class PrimeFactorization {

	private static boolean notStopped = true;
	private static boolean isPaused = false;
	private static List<Long> pauseTimes;
	private static List<Long> resumeTimes;
	private static long startTime;

	// The following method in this class returns an ArrayList of Arraylists
	// named primeFactorization
	// with dimensions 2 * the number of distinct prime factors in the number x.
	// primeFactorization.get(0) contains the primes, and
	// primeFactorization.get(1) contains the quantity of each prime
	// where the values and the indeces of the two ArrayLists match up.

	public static List<ArrayList<Long>> primeFactorization(Long x) {

		startTime = System.currentTimeMillis();

		// For subtracting pause and resume intervals from runtime
		pauseTimes = new ArrayList<Long>();
		resumeTimes = new ArrayList<Long>();

		ArrayList<Long> prime = new ArrayList<Long>();
		ArrayList<Long> primequant = new ArrayList<Long>();
		boolean added = false;
		long size = 0;
		long i = 0L;

		i = x;

		try {

			for (long j = 2L; j <= i / j; j++) {

				if (notStopped) {

					while (isPaused) {

						Thread.sleep(100);

					}

					while (i % j == 0) {

						if (notStopped) {

							while (isPaused) {

								Thread.sleep(100);

							}

							// search for factor in prime
							for (int k = 0; k < prime.size(); k++) {

								if (notStopped) {

									while (isPaused) {

										Thread.sleep(100);

									}

									if (prime.get(k) == j) {

										size = primequant.get(k);
										primequant.remove(k);
										primequant.add(k, size + 1);
										added = true;

									}
								} else {
									break;
								}

							}

							if (!added) {

								prime.add(j);
								primequant.add((long) 1);

							}

							i /= j;

							added = false;

						} else {

							break;

						}

					}

				} else {

					break;

				}
			}

			if (i > 1) {

				// search for factor in prime
				for (int k = 0; k < prime.size(); k++) {

					if (notStopped) {

						while (isPaused) {

							Thread.sleep(100);

						}

						if (prime.get(k) == i) {

							size = primequant.get(k);
							primequant.remove(k);
							primequant.add(k, size + 1);
							added = true;

						}

					} else {

						break;

					}

				}

				if (!added) {

					prime.add(i);
					primequant.add((long) 1);

				}

				added = false;

			}

		} catch (Exception e) {

			System.out
					.println("Not enough memory available to perform calculation.");
			System.out.println();
			isPaused = false;

		}

		List<ArrayList<Long>> primeFactorization = new ArrayList<ArrayList<Long>>();

		primeFactorization.add(prime);
		primeFactorization.add(primequant);

		if (notStopped) {
			return primeFactorization;
		} else {
			return null;
		}

	}

	public static void Stop() {

		notStopped = false;
		isPaused = false;

	}

	public static void Reinitialize() {

		notStopped = true;
		isPaused = false;

	}

	public static void Pause() {

		pauseTimes.add(System.currentTimeMillis());
		isPaused = true;

	}

	public static void Resume() {

		resumeTimes.add(System.currentTimeMillis());
		isPaused = false;

	}

	public static void PrintRuntime() {

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;

		// Subtract pause and resume intervals from totalTime

		for (int t = 0; t < pauseTimes.size(); t++) {

			totalTime -= (resumeTimes.get(t) - pauseTimes.get(t));

		}

		System.out.println("Runtime: " + totalTime + " ms");
		System.out.println();

		pauseTimes.clear();
		resumeTimes.clear();
		isPaused = false;

	}

}
