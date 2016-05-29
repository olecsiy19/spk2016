package com.lab5;

public class GA {

	/* ГА параметри */
	private static final int tournamentSize = 5;// кількість проходів для
												// турнірної селекції

	private TourManager tourManager = null;

	public GA(TourManager tourManager) {
		this.tourManager = tourManager;
	}

	// Розвивається населенням більше одного покоління
	public Population evolvePopulation(Population pop) {
		Population newPopulation = new Population(pop.populationSize(), false, tourManager);

		int elitismOffset = 0;

		// кросовер населення
		// Цикл розміром нового населення і створення осіб поточного населення
		for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
			// Виберір батьків
			Tour parent1 = tournamentSelection(pop);
			Tour parent2 = tournamentSelection(pop);
			// кросовер батьків
			Tour child = crossover(parent1, parent2);
			// добавити нащадка до нової популяції
			newPopulation.saveTour(i, child);
		}

		// Провести мутацію нової популяції
		for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
			mutate(newPopulation.getTour(i));
		}

		return newPopulation;
	}

	// двохточкове впорядковуюче
	public Tour crossover(Tour parent1, Tour parent2) {
		// створити новий прохід нащадка
		Tour child = new Tour(tourManager);
		int p1 = (int) (Math.random() * parent1.tourSize());
		int p2 = (int) (Math.random() * parent1.tourSize());
		
		for (int i = 0; i < child.tourSize(); i++) {
			if (i >= p1 && i <= p2) {
				child.setCity(i, null);
			} else {
				child.setCity(i, parent1.getCity(i));
			}
		}
		
		int n = 0;
		for (int j = 0; j < parent2.tourSize(); j++) {
			boolean t = false;
			
			for (int k = 0; k < child.tourSize(); k++) {
				if (parent2.getCity(j) == child.getCity(k)) {
					t = true;
					break;
				}
			}
			if (t == false) {
				child.setCity(p1 + n, parent2.getCity(j));
				n = n + 1;
			}
		}

		return child;
	}

	// класичне інвертування
	private void mutate(Tour tour) {
		int tourPos1 = (int) (tour.tourSize() * Math.random());
		int tourPos2 = (int) (tour.tourSize() * Math.random());

		if (tourPos2 < tourPos1) {
			int q = tourPos1;
			tourPos1 = tourPos2;
			tourPos2 = q;
		}

		for (int i = 0; i <= (tourPos2 - tourPos1 + 1) / 2; i++) {
			City q = tour.getCity(tourPos1 + i);
			tour.setCity(tourPos1 + i, tour.getCity(tourPos2 - i));
			tour.setCity(tourPos2 - i, q);
		}
	}

	// Вибрати кандидатів проходу для кросинговера
	private Tour tournamentSelection(Population pop) {
		// Створити турнірну популяцію
		Population tournament = new Population(tournamentSize, false,
				tourManager);
		// Для кожного міста в турнірі отримати рандомного кандидата проходу і
		// добавити його
		for (int i = 0; i < tournamentSize; i++) {
			int randomId = (int) (Math.random() * pop.populationSize());
			tournament.saveTour(i, pop.getTour(randomId));
		}
		// взяти найкращий прохід
		Tour fittest = tournament.getFittest();
		return fittest;
	}
}