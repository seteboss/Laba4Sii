package servise;

import entity.TrainingSample;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackpropagationAlgorithm {

		private static final int INPUT_NEURONS = 625;

		private static final int HIDDEN_NEURONS = 500;

		private static final int OUTPUT_NEURONS = 5;

		private static final int  MAX_SAMPLES = 10;

		/* Веса */
		/* Вход скрытых ячеек (со смещением) */
		double[][] wih = new double[INPUT_NEURONS + 1][HIDDEN_NEURONS];

		/* Вход выходных ячеек (со смещением) */
		double[][] who = new double[HIDDEN_NEURONS + 1][OUTPUT_NEURONS];

		/* Активаторы */
		double[] inputs = new double[INPUT_NEURONS];

		double[] hidden = new double[HIDDEN_NEURONS];

		double[] target = new double[OUTPUT_NEURONS];

		double[] actual = new double[OUTPUT_NEURONS];

		/* Ошибки */
		double[] erro = new double[OUTPUT_NEURONS];

		double[] errh = new double[HIDDEN_NEURONS];

		double learnRate;

		int epoch;

		private Random random = new Random();

		List<TrainingSample> trainingSamples = new ArrayList<>(); //Обучающие образы

		private void initWeight() {
				for (int i = 0; i < INPUT_NEURONS + 1; i++) {
						for (int j = 0; j < HIDDEN_NEURONS; j++) {
								wih[i][j] = random.nextDouble() - 0.5;
						}
				}
				for (int i = 0; i < HIDDEN_NEURONS + 1; i++) {
						for (int j = 0; j < OUTPUT_NEURONS; j++) {
								who[i][j] = random.nextDouble() - 0.5;
						}
				}
		}


		double sigmoid(double val) {
				return (1.0 / (1.0 + Math.exp(-val)));
		}

		double sigmoidDerivative(double val) {
				return (val * (1.0 - val));
		}


		void feedForward() {
				int inp, hid, out;
				double sum;

				/* Вычислить вход в скрытый слой */
				for (hid = 0; hid < HIDDEN_NEURONS; hid++) {
						sum = 0.0;
						for (inp = 0; inp < INPUT_NEURONS; inp++) {
								sum += inputs[inp] * wih[inp][hid];
						}
						/* Добавить смещение */
						sum += wih[INPUT_NEURONS][hid];
						hidden[hid] = sigmoid(sum);
				}
				/* Вычислить вход в выходной слой */
				for (out = 0; out < OUTPUT_NEURONS; out++) {
						sum = 0.0;
						for (hid = 0; hid < HIDDEN_NEURONS; hid++) {
								sum += hidden[hid] * who[hid][out];
						}
						/* Добавить смещение */
						sum += who[HIDDEN_NEURONS][out];
						actual[out] = sigmoid(sum);
				}
		}

		void backPropagate() {

				int inp, hid, out;
				/* Вычислить ошибку выходного слоя (шаг 3 для выходных ячеек) */
				for (out = 0; out < OUTPUT_NEURONS; out++) {
						erro[out] = (target[out] - actual[out]) * sigmoidDerivative(actual[out]);
				}  /* Вычислить ошибку скрытого слоя (шаг 3 для скрытого слоя) */
				for (hid = 0; hid < HIDDEN_NEURONS; hid++) {
						errh[hid] = 0.0;
						for (out = 0; out < OUTPUT_NEURONS; out++) {
								errh[hid] += erro[out] * who[hid][out];
						}
						errh[hid] *= sigmoidDerivative(hidden[hid]);
				}
				/* Обновить веса для выходного слоя (шаг 4 для выходных ячеек) */
				for (out = 0; out < OUTPUT_NEURONS; out++) {
						for (hid = 0; hid < HIDDEN_NEURONS; hid++) {
								who[hid][out] += (learnRate * erro[out] * hidden[hid]);
						}
						/* Обновить смещение */
						who[HIDDEN_NEURONS][out] += (learnRate * erro[out]);
				}
				/* Обновить веса для скрытого слоя (шаг 4 для скрытого слоя) */
				for (hid = 0; hid < HIDDEN_NEURONS; hid++) {
						for (inp = 0; inp < INPUT_NEURONS; inp++) {
								wih[inp][hid] += (learnRate * errh[hid] * inputs[inp]);
						}
						/* Обновить смещение */
						wih[INPUT_NEURONS][hid] += (learnRate * errh[hid]);
				}
		}


		public void training(){
				double err;
				int i, sample=0, iterations=0;
				int sum = 0;
				int ssum = 0;

				while (true){

						if (++sample == MAX_SAMPLES){
								sample = 0;
						}

						inputs = trainingSamples.get(sample).getVectorOfInputValues();
						target = trainingSamples.get(sample).getVectorOfOutputValues();

						feedForward();

						err = 0;

						for (int j = 0; j < OUTPUT_NEURONS; j++) {
								err += Math.sqrt(trainingSamples.get(sample).getVectorOfOutputValues()[j] - actual[j]);
						}
						err = 0.5 * err;

						if (iterations++ > epoch){
								break;
						}

						backPropagate();
				}

		}

		public void recognition(int numImage){
				inputs = trainingSamples.get(numImage).getVectorOfInputValues();
				feedForward();
		}


		public void initTrainingSamples(int size) throws IOException {
				trainingSamples.add(new TrainingSample());
				trainingSamples.get(0).init("src/main/resources/trainingImages/airplane.jpg", 5, 1, size);
				trainingSamples.add(new TrainingSample());
				trainingSamples.get(1).init("src/main/resources/trainingImages/fly.jpg", 5, 1, size);
				trainingSamples.add(new TrainingSample());
				trainingSamples.get(2).init("src/main/resources/trainingImages/car.jpg", 5, 2, size);
				trainingSamples.add(new TrainingSample());
				trainingSamples.get(3).init("src/main/resources/trainingImages/car2.jpg", 5, 2, size);
				trainingSamples.add(new TrainingSample());
				trainingSamples.get(4).init("src/main/resources/trainingImages/moto.jpg", 5, 3, size);
				trainingSamples.add(new TrainingSample());
				trainingSamples.get(5).init("src/main/resources/trainingImages/moto2.jpg", 5, 3, size);
				trainingSamples.add(new TrainingSample());
				trainingSamples.get(6).init("src/main/resources/trainingImages/ship.jpg", 5, 4, size);
				trainingSamples.add(new TrainingSample());
				trainingSamples.get(7).init("src/main/resources/trainingImages/ship2.jpg", 5, 4, size);
				trainingSamples.add(new TrainingSample());
				trainingSamples.get(8).init("src/main/resources/trainingImages/train.jpg", 5, 5, size);
				trainingSamples.add(new TrainingSample());
				trainingSamples.get(9).init("src/main/resources/trainingImages/train2.jpg", 5, 5, size);
		}

}
