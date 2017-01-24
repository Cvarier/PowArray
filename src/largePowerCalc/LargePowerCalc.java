package largePowerCalc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LargePowerCalc {

	private static boolean notStopped = true;
	private static boolean isPaused = false;
	private static List<Long> pauseTimes;
	private static List<Long> resumeTimes;

	public static void exponentiate(int x, int n) {

		try {

			// For subtracting pause and resume intervals from runtime
			pauseTimes = new ArrayList<Long>();
			resumeTimes = new ArrayList<Long>();

			int i = 0;

			// Calculate # digits in number
			int digitsFinal = (int) Math.floor(n * Math.log10(x) + 1);

			// ------------Print the number of digits in x^n--------------------

			List<Byte> ndigits = new ArrayList<Byte>();
			int ncopy = n;

			while (ncopy > 0) {

				ndigits.add((byte) (ncopy % 10));
				ncopy /= 10;

			}

			Collections.reverse(ndigits);

			System.out.print("The number of digits in " + x);

			for (int z : ndigits) {

				if (z == 0) {

					System.out.print("'");

				} else if (z == 1) {

					System.out.print("¹");

				} else if (z == 2) {

					System.out.print("²");

				} else if (z == 3) {

					System.out.print("³");

				} else if (z == 4) {

					System.out.print("!");

				} else if (z == 5) {

					System.out.print("\"");

				} else if (z == 6) {

					System.out.print("#");

				} else if (z == 7) {

					System.out.print("$");

				} else if (z == 8) {

					System.out.print("%");

				} else {

					System.out.print("&");

				}

			}

			System.out.print(" is " + digitsFinal + " digits");
			System.out.println();
			System.out.println();

			// ------------------------------------------------------------

			byte[] finalprod = new byte[digitsFinal + 5];
			finalprod[finalprod.length - 1] = 1;
			int digprod = 0;
			int carryover = 0;
			// int zerocount = 0;
			i = finalprod.length - 1;

			int curind = 0;

			int digits = 0;
			List<Byte> digarray = new ArrayList<Byte>();

			// Store how many factors of 10 base x has, store it in facTens and
			// strip x of its trailing zeroes
			int facTens = 0;
			int xcopy = x;

			while (x % 10 == 0) {

				facTens++;
				x /= 10;

			}
			/*
			 * // ---------------Optimization Algorithm v 1.0 goes
			 * here:----------
			 * 
			 * // Check if x^n > 10 digits in length, and only execute
			 * optimization // if it is
			 * 
			 * boolean needsOptimization = false; boolean optimized = false;
			 * 
			 * int nr = 0; int xOld = 0; int nOld = n; int xr = 0;
			 * 
			 * if (digitsFinal > 10 && Math.log10(x) != (int) Math.log10(x)) {
			 * 
			 * needsOptimization = true; int maxpow = 1;
			 * 
			 * while (true) {
			 * 
			 * if ((long) Math.pow(x, maxpow) < (long) Math.pow(2, 20) && (long)
			 * Math.pow(x, maxpow + 1) < (long) Math.pow(2, 20)) { maxpow++; }
			 * else { break; }
			 * 
			 * }
			 * 
			 * // store the current value of x xOld = x;
			 * 
			 * // change the value of x to the highest power of x that is less
			 * // than 2^20 x = (int) Math.pow(x, maxpow);
			 * 
			 * // calculate the new value of n n = n / maxpow;
			 * 
			 * // get the remaining factors of xOld nr = nOld % maxpow;
			 * 
			 * // store remaining factors of xOld in one int xr = (int)
			 * Math.pow(xOld, nr);
			 * 
			 * }
			 * 
			 * // ---------------Optimization Algorithm ends
			 * here------------------
			 */

			// Get the starting epoch time
			long startTime = System.currentTimeMillis();

			// If x^n does not only contain powers of 10, execute main algorithm
			if (Math.log10(x) != (int) Math.log10(x)) {

				// For Adding Lines in Multi-Digit Multiplication
				int sumcarry = 0;
				int digsum = 0;
				// boolean zerook = false;

				// Getting number of digits in curind
				curind = x;

				while (curind > 0) {

					digarray.add((byte) (curind % 10));
					curind /= 10;
					digits++;

				}

				Collections.reverse(digarray);
				curind = x;

				for (int mainind = 0; mainind < n; mainind++) {

					if (notStopped) {

						// Pause/Resume Calculation

						while (isPaused) {

							Thread.sleep(100);

						}

						byte[][] lines = new byte[digits][finalprod.length]; // An
																				// Array
																				// for
																				// each
																				// line
																				// in
																				// the
																				// multiplication.
																				// once
																				// these
																				// are
																				// filled,
																				// they
																				// will
																				// be
																				// added
																				// together.

						byte[][] temparray = new byte[digits - 1][finalprod.length];

						if (digits > 1) {

							for (int init = 0; init < finalprod.length; init++) {

								for (int lineind = 0; lineind < digits; lineind++) {

									lines[lineind][init] = finalprod[init]; // initialize
																			// lines
																			// with
																			// value
																			// of
																			// finalprod

								}

							}

							// fill lines
							for (int z = 0; z < digits; z++) {

								{
									while (// zerocount < 100 &&
									i >= 0) {

										digprod = lines[z][i] * digarray.get(z);

										lines[z][i] = (byte) ((digprod % 10 + carryover) % 10);
										carryover = (digprod + carryover) / 10;

										if (i > 2 && lines[z][i] == 0
												&& lines[z][i - 1] == 0
												&& lines[z][i - 2] == 0
												&& lines[z][i - 3] == 0) {

											// zerocount++;

										}

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
							// add necessary zeros to each line array in lines
							// at beginning

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

							for (int init = 0; init < finalprod.length; init++) {

								finalprod[init] = 0;

							}

							// add lines together after multiplying and store in
							// finalprod

							while (// zerocount < 10 &&
							i >= 0) {

								for (int digsumind = 0; digsumind < digits; digsumind++) {

									digsum += lines[digsumind][i];

								}

								finalprod[i] = (byte) ((digsum % 10 + sumcarry) % 10);
								sumcarry = (digsum + sumcarry) / 10;

								/*
								 * for (int digsumind = 0; digsumind < digits;
								 * digsumind++) {
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

							while (// zerocount < 100 &&
							i >= 0) {

								digprod = finalprod[i] * curind;

								finalprod[i] = (byte) ((digprod % 10 + carryover) % 10);
								carryover = (digprod + carryover) / 10;

								/*
								 * if (i > 2 && finalprod[i] == 0 && finalprod[i
								 * - 1] == 0 && finalprod[i - 2] == 0 &&
								 * finalprod[i - 3] == 0) {
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

						/*
						 * // ---------------Optimization Algorithm v 1.0
						 * continues // here:-----
						 * 
						 * // multiply end result with remaining factors:
						 * xOld^nr if (mainind == n - 1 && needsOptimization &&
						 * !optimized) {
						 * 
						 * mainind = 0; n = 2; x = xr; optimized = true;
						 * 
						 * }
						 * 
						 * // ---------------Optimization Algorithm ends //
						 * here------------------
						 */

					} else {

						break;

					}

				}
			}

			// Calculate runtime

			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;

			// Subtract pause and resume intervals from totalTime

			for (int t = 0; t < pauseTimes.size(); t++) {

				totalTime -= (resumeTimes.get(t) - pauseTimes.get(t));

			}

			if (notStopped) {

				// Give x and n the original value they had initially. Print the
				// String: "x^n"

				x = xcopy;
				// n = nOld;

				int finalsum = 0;
				List<Byte> finalprodarray = new ArrayList<Byte>();

				System.out.print(x + "");

				for (int z : ndigits) {

					if (z == 0) {

						System.out.print("'");

					} else if (z == 1) {

						System.out.print("¹");

					} else if (z == 2) {

						System.out.print("²");

					} else if (z == 3) {

						System.out.print("³");

					} else if (z == 4) {

						System.out.print("!");

					} else if (z == 5) {

						System.out.print("\"");

					} else if (z == 6) {

						System.out.print("#");

					} else if (z == 7) {

						System.out.print("$");

					} else if (z == 8) {

						System.out.print("%");

					} else {

						System.out.print("&");

					}

				}

				System.out.println(" =");
				System.out.println();

				int trailzeroes = 0;

				// If x^n does not only contain powers of 10, remove all the
				// excess trailing zeroes in the final product ArrayList

				if (Math.log10(x) != (int) Math.log10(x)) {

					// When x^n does not only contain powers of 10, calculate
					// the sum of digits in x^n and store its value in an
					// ArrayList

					for (int a = finalprod.length - 1; a >= 0; a--) {

						finalsum += finalprod[a];

						// transfer the contents of finalprod to an ArrayList,
						// finalprodarray

						finalprodarray.add(finalprod[a]);

					}

					Collections.reverse(finalprodarray);

					while (finalprodarray.get(0) == 0
							&& finalprodarray.get(1) == 0) {

						finalprodarray.remove(0);

					}

					finalprodarray.remove(0);

					// Add back the number of trailing zeroes x^n is supposed to
					// have if x contains factors of 10

					for (int b = 0; b < facTens * n; b++) {

						finalprodarray.add((byte) 0);
						trailzeroes++;

					}

				} else {

					finalprodarray.clear();

					for (int q = 0; q < digitsFinal - 1; q++) {

						finalprodarray.add((byte) 0);

					}

					finalprodarray.add((byte) 1);
					Collections.reverse(finalprodarray);

					// When x^n does contains only a power of 10, the sum of the
					// digits in x^n is 1
					finalsum = 1;

				}

				// Print the calculated value of x^n

				int charcount = 0;

				for (int a = 0; a < finalprodarray.size(); a++) {

					if (charcount == 44) {

						System.out.println();
						charcount = 0;

					}

					System.out.print(finalprodarray.get(a));
					charcount++;

				}

				// Print the sum of digits in x^n

				System.out.println();
				System.out.println();
				System.out.print("The sum of the digits in " + x);

				for (int z : ndigits) {

					if (z == 0) {

						System.out.print("'");

					} else if (z == 1) {

						System.out.print("¹");

					} else if (z == 2) {

						System.out.print("²");

					} else if (z == 3) {

						System.out.print("³");

					} else if (z == 4) {

						System.out.print("!");

					} else if (z == 5) {

						System.out.print("\"");

					} else if (z == 6) {

						System.out.print("#");

					} else if (z == 7) {

						System.out.print("$");

					} else if (z == 8) {

						System.out.print("%");

					} else {

						System.out.print("&");

					}

				}

				System.out.print(" = " + finalsum);
				System.out.println();
				System.out.println();

				// Print the # of trailing zeroes in x^n

				if (trailzeroes > 0) {

					System.out.print("The number of trailing zeroes in " + x);

					for (int z : ndigits) {

						if (z == 0) {

							System.out.print("'");

						} else if (z == 1) {

							System.out.print("¹");

						} else if (z == 2) {

							System.out.print("²");

						} else if (z == 3) {

							System.out.print("³");

						} else if (z == 4) {

							System.out.print("!");

						} else if (z == 5) {

							System.out.print("\"");

						} else if (z == 6) {

							System.out.print("#");

						} else if (z == 7) {

							System.out.print("$");

						} else if (z == 8) {

							System.out.print("%");

						} else {

							System.out.print("&");

						}
					}

					System.out.print(" is " + trailzeroes);
					System.out.println();
					System.out.println();

				}
			}

			// Print Runtime

			System.out.println("Runtime: " + totalTime + " ms");
			System.out.println();

			pauseTimes.clear();
			resumeTimes.clear();
			isPaused = false;

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
