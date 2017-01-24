package factorialCalc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FactorialCalc {

	private static boolean notStopped = true;
	private static boolean isPaused = false;
	private static List<Long> pauseTimes;
	private static List<Long> resumeTimes;

	/*
	 * private static int zerocountUpperDigits = 3; private static int
	 * zerocountUpper = 10000; private static int zerocountCurrent = 1000;
	 */

	public static void factorialize(int x, int finalprodsize) {

		try {

			// For subtracting pause and resume intervals from runtime
			pauseTimes = new ArrayList<Long>();
			resumeTimes = new ArrayList<Long>();

			// Estimate and print the number of digits in the factorial using
			// the value of
			// finalprodsize

			System.out.println("The number of digits in " + x
					+ "! is less than or equal to "
					+ (finalprodsize - 2 + " digits"));
			System.out.println();

			// -----Find Prime Factorization of 100!-------

			// In a loop i = 2 to i <= x, find the prime factorization for i.
			// Search for
			// each prime factor in an Integer ArrayList called prime. If it is
			// there, then in another
			// Integer ArrayList describing the amount of times
			// that factor appears called primequant (the indices match
			// , meaning that the index ind of the prime array corresponds to
			// the same index in the
			// primequant array) add one. If it isn't there, put the prime
			// factor in prime, and
			// initialize the quantity of that prime to be one in primequant.

			ArrayList<Integer> prime = new ArrayList<Integer>();
			ArrayList<Integer> primequant = new ArrayList<Integer>();
			boolean added = false;
			int size = 0;
			int i = 0;

			// Get the starting epoch time
			long startTime = System.currentTimeMillis();

			for (int n = 2; n <= x; n++) {

				i = n;

				if (notStopped) {

					while (isPaused) {

						Thread.sleep(100);

					}

					for (int j = 2; j <= i / j; j++) {

						if (notStopped) {

							while (isPaused) {

								Thread.sleep(100);

							}

							while (i % j == 0) {
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
											break;

										}

									} else {
										break;
									}

								}

								if (!added) {

									prime.add(j);
									primequant.add(1);

								}

								i /= j;

								added = false;

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
									break;

								}

							} else {
								break;
							}

						}

						if (!added) {

							prime.add(i);
							primequant.add(1);

						}

						added = false;

					}

				} else {
					break;
				}

			}

			// Calculate the # of trailing zeros and subtract factors of five
			// from factors of two
			// Update ArrayList prime

			int twocount = 0;
			int fivecount = 0;
			int trailzeros = 0;

			if (notStopped) {

				for (int k = 0; k < prime.size(); k++) {

					if (prime.get(k) == 5) {

						fivecount = primequant.get(k);
						prime.remove(k);
						primequant.remove(k);
						break;

					}

				}

				for (int k = 0; k < prime.size(); k++) {

					if (prime.get(k) == 2) {

						twocount = primequant.get(k);

						primequant.remove(k);
						primequant.add(k, twocount - fivecount);
						break;

					}

				}

			}

			trailzeros = fivecount;

			// Multiply each of the primes together in the ArrayList prime
			// and store the result in a new ArrayList called finalprod. (Reuse
			// the power digit algorithm in P16)

			byte[] finalprod = new byte[finalprodsize];
			finalprod[finalprod.length - 1] = 1;
			int digprod = 0;
			int carryover = 0;
			// int zerocount = 0;
			i = finalprod.length - 1;

			int curprime = 0; // cuprime to the power of curpow will be
								// calculated
			int curpow = 0;

			int digits = 0;
			List<Byte> digarray = new ArrayList<Byte>();

			// For Adding Lines in Multi-Digit Multiplication
			int sumcarry = 0;
			int digsum = 0;
			// boolean zerook = false;

			for (int pri = 0; pri < prime.size(); pri++) {

				if (notStopped) {

					// Pause/Resume Calculation

					while (isPaused) {

						Thread.sleep(100);

					}

					curprime = prime.get(pri);
					curpow = primequant.get(pri);

					// Getting number of digits in curprime

					while (curprime > 0) {

						digarray.add((byte) (curprime % 10));
						curprime /= 10;
						digits++;

					}

					/*
					 * if (digits > zerocountUpperDigits) zerocountCurrent =
					 * zerocountUpper;
					 */

					Collections.reverse(digarray);
					curprime = prime.get(pri);

					for (int pow = 0; pow < curpow; pow++) {

						if (notStopped) {

							// Pause/Resume Calculation

							while (isPaused) {

								Thread.sleep(100);

							}

							if (digits > 1) {

								// An ArrayList for each line in the
								// multiplication.
								// once these are filled, they will
								// be added together.

								byte[][] lines = new byte[digits][finalprod.length];

								byte[][] temparray = new byte[digits - 1][finalprod.length];

								for (int init = 0; init < finalprod.length; init++) {

									for (int lineind = 0; lineind < digits; lineind++) {

										// initialize lines with
										// value of finalprod

										lines[lineind][init] = finalprod[init];

									}

								}

								// fill lines
								for (int z = 0; z < digits; z++) {

									{
										while (// zerocount < zerocountCurrent
												// &&
										i >= 0) {

											digprod = lines[z][i]
													* digarray.get(z);

											lines[z][i] = (byte) ((digprod % 10 + carryover) % 10);
											carryover = (digprod + carryover) / 10;

											/*
											 * if (i > 2 && lines[z][i] == 0 &&
											 * lines[z][i - 1] == 0 &&
											 * lines[z][i - 2] == 0 &&
											 * lines[z][i - 3] == 0) {
											 * 
											 * zerocount++;
											 * 
											 * }
											 */

											i--;
										}

										carryover = 0;
										// zerocount = 0;
										i = finalprod.length - 1;

									}

									if (digarray.get(z) == 0) {

										for (int run = 0; run < finalprod.length; run++) {

											lines[z][run] = 0;

										}

									}

								}

								// Store value of lines[0 to digits -2][init] in
								// temparray

								for (int init = 0; init < finalprod.length; init++) {

									for (int tempind = 0; tempind < digits - 1; tempind++) {

										temparray[tempind][init] = lines[tempind][init];

									}

								}

								// add necessary zeros to each line array in
								// lines at beginning

								for (int q = 0; q < digits - 1; q++) {

									for (int zeroind = digits - q - 1; zeroind >= 0; zeroind--) {

										lines[q][finalprod.length - 1 - zeroind] = 0;

									}

									for (int index = finalprod.length
											- (digits - q); index >= 0; index--) {

										lines[q][index] = temparray[q][index
												+ (digits - q - 1)];

									}

								}

								// clear finalprod

								/*
								 * if (curprime > primesize) {
								 * 
								 * prevsize = finalprod.length; finalprod = new
								 * int[newprodsize];
								 * 
								 * }
								 */

								for (int init = 0; init < finalprod.length; init++) {

									finalprod[init] = 0;

								}

								// add lines together after multiplying and
								// store in
								// finalprod

								while (// zerocount < zerocountCurrent &&
								i >= 0) {

									for (int digsumind = 0; digsumind < digits; digsumind++) {

										digsum += lines[digsumind][i];

									}

									finalprod[i] = (byte) ((digsum % 10 + sumcarry) % 10);
									sumcarry = (digsum + sumcarry) / 10;

									/*
									 * for (int digsumind = 0; digsumind <
									 * digits; digsumind++) {
									 * 
									 * if (i > 2 && lines[digsumind][i] == 0 &&
									 * lines[digsumind][i - 1] == 0 &&
									 * lines[digsumind][i - 2] == 0 &&
									 * lines[digsumind][i - 3] == 0) {
									 * 
									 * zerook = true;
									 * 
									 * } else {
									 * 
									 * zerook = false;
									 * 
									 * }
									 * 
									 * }
									 * 
									 * if (zerook) {
									 * 
									 * zerocount++;
									 * 
									 * }
									 * 
									 * if (!zerook) {
									 * 
									 * zerocount = 0;
									 * 
									 * }
									 */

									i--;
									// zerook = false;
									digsum = 0;
								}

								sumcarry = 0;
								// zerocount = 0;
								i = finalprod.length - 1;

							} else {

								while (// zerocount < zerocountCurrent &&
								i >= 0) {

									digprod = finalprod[i] * curprime;

									finalprod[i] = (byte) ((digprod % 10 + carryover) % 10);
									carryover = (digprod + carryover) / 10;

									/*
									 * if (i > 2 && finalprod[i] == 0 &&
									 * finalprod[i - 1] == 0 && finalprod[i - 2]
									 * == 0 && finalprod[i - 3] == 0) {
									 * 
									 * zerocount++;
									 * 
									 * }
									 */

									i--;
								}

								carryover = 0;
								// zerocount = 0;
								i = finalprod.length - 1;

							}

						} else {
							break;
						}

					}

					digits = 0;
					digarray.clear();

				} else {
					break;
				}

			}

			// Print Runtime
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;

			// Subtract pause and resume intervals from totalTime

			for (int t = 0; t < pauseTimes.size(); t++) {

				totalTime -= (resumeTimes.get(t) - pauseTimes.get(t));

			}

			if (notStopped) {

				// ------Find the sum of the digits in the new product.-------

				int finalsum = 0;
				List<Byte> finalprodarray = new ArrayList<Byte>();

				System.out.println(x + "! = ");
				System.out.println();

				// Print x! in an array

				for (int a = finalprod.length - 1; a >= 0; a--) {

					finalsum += finalprod[a];
					finalprodarray.add(finalprod[a]);

				}

				Collections.reverse(finalprodarray);

				while (finalprodarray.get(0) == 0 && finalprodarray.get(1) == 0) {

					finalprodarray.remove(0);

				}

				finalprodarray.remove(0);

				// Put trailing zeroes at the end of the finalprodarray

				for (int b = 0; b < trailzeros; b++) {

					finalprodarray.add(finalprodarray.size(), (byte) 0);

				}

				// Print x!

				int charcount = 0;

				for (int b = 0; b < finalprodarray.size(); b++) {

					if (charcount == 44) {

						System.out.println();
						charcount = 0;

					}

					System.out.print(finalprodarray.get(b));
					charcount++;

				}

				// Print the answer

				System.out.println();
				System.out.println();
				System.out.println("The sum of the digits in " + x + "! = "
						+ finalsum);
				System.out.println();
				System.out.println("The precise number of digits in " + x
						+ "! is " + finalprodarray.size() + " digits");
				System.out.println();
				System.out.println("The number of trailing zeros in " + x
						+ "! is " + trailzeros);
				System.out.println();

			}

			System.out.println("Runtime: " + totalTime + " ms");
			System.out.println();

			pauseTimes.clear();
			resumeTimes.clear();
			isPaused = false;
			// zerocountCurrent = 1000;

		} catch (Exception e1) {

			System.out
					.println("Not enough memory available to perform calculation.");
			System.out.println();
			isPaused = false;

		} catch (OutOfMemoryError e2) {

			System.out
					.println("Not enough memory available to perform calculation.");
			System.out.println();
			isPaused = false;

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

}
