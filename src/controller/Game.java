package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import players.BioterroristBot;
import players.MedicBot;
import players.PassiveBot;

public class Game {
	private static Player[] players = {
		new PassiveBot(),
		new MedicBot(),
		new BioterroristBot()
	};
	
	// Game Parameters
	private static final int ROUNDS = 50;
	
	// Console
	private static final boolean DEBUG = true;
	private static final boolean GAME_MESSAGES = true;
	
	private static final int START_SANE = 99;
	private static final int START_INFECTED = 1;
	private static final int START_DEAD = 0;
	private static final int START_INFECTION = 2;
	private static final int START_CONTAGION = 5;
	private static final int START_LETHALITY = 10;
	private static final int START_MIGRATION = 5;
		
	private static final int BIRTH_ROUND = 5;	
	
	private static final int MUTATION_INFECTION = 2;
	private static final int MUTATION_CONTAGION = 5;
	private static final int MUTATION_LETHALITY = 5;
	
	private static final int MICROBIOLOGY_INFECTION = 4;
	private static final int EPIDEMIOLOGY_CONTAGION = 8;
	private static final int IMMUNOLOGY_LETHALITY = 4;
	private static final int VACCINATION_INFECTION = 1;
	private static final int VACCINATION_CONTAGION = 4;
	private static final int VACCINATION_LETHALITY = 2;
	
	private static final int CURE_INFECTED = 10;
	private static final int QUARANTINE_INFECTION = 30;
	
	private static final int OPEN_MIGRATION = 10;
	private static final int CLOSE_MIGRATION = 10;
	
	private static final int BIOTERRORISM_INFECTED = 4;
	
	private static final int DISSEMINATION_INFECTION = 1;
	private static final int DISSEMINATION_CONTAGION = 2;
	
	private static final int WEAPONIZATION_INFECTION = 1;
	private static final int WEAPONIZATION_LETHALITY = 2;
	
	private static final int PACIFICATION_INFECTION = 1;
	private static final int PACIFICATION_CONTAGION = 1;
	private static final int PACIFICATION_LETHALITY = 1;
	
	private final List<State> states = new ArrayList<State>();
	private int round = 0;
	
	public Game() {
		for (int i = 0; i < players.length; i++) {
			players[i].setId(i);
		}
	}
	
	public static void main(String... args) {
		// Starting
		new Game().run();
	}	
	
	public void run() {
			
		if (GAME_MESSAGES) 
			System.out.println("Starting a new game...");
		
		this.initialize();
		
		if (GAME_MESSAGES) 
			System.out.println("Game begins.");
							
		for (round = 1; round <= ROUNDS; round++) {
			if (GAME_MESSAGES) {
				System.out.println("======== Round : " +  round + " ========");
			}
			if (!makeTurns()) break; //break if only no player left
		}
	
		printResults();
	}
	
	private void initialize() {		
		
		for (int i = 0; i < players.length; i++) {
			try {
				if (GAME_MESSAGES) System.out.println("Player \"" + players[i].getDisplayName() + "\" added.");
				State State = new State(i, players[i], START_SANE, START_INFECTED, START_DEAD, START_INFECTION, START_CONTAGION, START_LETHALITY, START_MIGRATION);
				states.add(State);
			} catch (Exception e) {
				if (DEBUG) {
					System.out.println("Exception in initialize() by " + players[i].getDisplayName());
					e.printStackTrace();
				}
			}
		}
		
		Collections.shuffle(states);
	}	
	
	private boolean makeTurns() {
		
		// Phase 1 : Mutation
		if (onePlayerLeft()) return false;
		if (DEBUG) System.out.println("--- Phase 1 : Mutation ---");
		mutation();
				
		// Phase 2 : Reproduction
		if (onePlayerLeft()) return false;
		if (DEBUG) System.out.println("--- Phase 2 : Reproduction ---");
		reproduction();
				
		// Phase 3 : Migration
		if (onePlayerLeft()) return false;
		if (DEBUG) System.out.println("--- Phase 3 : Migration ---");
		migration();
		
		// Phase 4 : Infection
		if (onePlayerLeft()) return false;
		if (DEBUG) System.out.println("--- Phase 4 : Infection ---");
		infection();
				
		// Phase 5 : Contagion
		if (onePlayerLeft()) return false;
		if (DEBUG) System.out.println("--- Phase 5 : Contagion ---");
		contagion();
		
		// Phase 6 : Extinction
		if (onePlayerLeft()) return false;
		if (DEBUG) System.out.println("--- Phase 6 : Extinction ---");
		extinction();	
		
		// Phase 7 : Player Turn
		if (onePlayerLeft()) return false;
		if (DEBUG) System.out.println("--- Phase 7 : Players ---");
		for (State state : states) {
			
			if (state.isAlive()) {
				Player owner = state.getOwner();
				try {
					String request = round + ";" + owner.getId() + generateArgs();
					String response = state.getCommand(request);
					if (DEBUG) {
						System.out.println("Request : " + request);
						System.out.println("Response : " + response);
					}
					
					if (response.length() < 3) {
						throw new Exception("Invalid response length : " + response.length());
					}
					
					for (int i = 0; i < 3; i++) {
						switch (response.charAt(i)) {
							case 'M': executeMicrobiology(state); break;
							case 'E': executeEpidemiology(state); break;
							case 'I': executeImmunology(state); break;
							case 'V': executeVaccination(state); break;
							case 'C': executeCure(state); break;
							case 'Q': executeQuarantine(state); break;
							case 'O': executeOpenBorders(state); break;
							case 'B': executeCloseBorders(state); break;
							case 'T': executeBioterrorism(state); break;
							case 'D': executeDissemination(state); break;
							case 'W': executeWeaponization(state); break;
							case 'P': executePacification(state); break;
							case 'N': executeWait(state); break;
							default : executeWait(state); break;
						}
					}			
					
				} catch (Exception e) {
					if (DEBUG) {
						System.out.println("Exception in makeTurns() by " + owner.getDisplayName());
						e.printStackTrace();
					}
				}
			
				
				if (onePlayerLeft()) return false;
			}
		}
		
		return true;
	}
	
	private boolean onePlayerLeft() {
		
		int alive = 0;
		for (State state : states) {
			alive += state.isAlive() ? 1 : 0;
		}
				
		return alive <= 1;
	}
	
	private void executeMicrobiology(State state) {
		state.setInfectionRate(Math.max(0, state.getInfectionRate() - MICROBIOLOGY_INFECTION));
		
		if (GAME_MESSAGES) System.out.println(state.getOwner().getDisplayName() + " reduced local infection rate by " + MICROBIOLOGY_INFECTION + " (" + state.getInfectionRate() + ")");
	}
	
	private void executeEpidemiology(State state) {
		state.setContagionRate(Math.max(0, state.getContagionRate() - EPIDEMIOLOGY_CONTAGION));
	
		if (GAME_MESSAGES) System.out.println(state.getOwner().getDisplayName() + " reduced local contagion rate by " + EPIDEMIOLOGY_CONTAGION + "% (" + state.getContagionRate() + "%)");
	}
	
	private void executeImmunology(State state) {
		state.setLethalityRate(Math.max(0, state.getLethalityRate() - IMMUNOLOGY_LETHALITY));
	
		if (GAME_MESSAGES) System.out.println(state.getOwner().getDisplayName() + " reduced local lethality rate by " + IMMUNOLOGY_LETHALITY + "% (" + state.getLethalityRate() + "%)");
	}
	
	private void executeVaccination(State state) {
		state.setInfectionRate(Math.max(0, state.getInfectionRate() - VACCINATION_INFECTION));
		state.setContagionRate(Math.max(0, state.getContagionRate() - VACCINATION_CONTAGION));
		state.setLethalityRate(Math.max(0, state.getLethalityRate() - VACCINATION_LETHALITY));	
	
		if (GAME_MESSAGES) System.out.println(state.getOwner().getDisplayName() + " reduced local infection rate by " + VACCINATION_INFECTION + " (" + state.getInfectionRate() + "), local contagion rate by "
		+ VACCINATION_CONTAGION + "% (" + state.getContagionRate() + "%), local lethality rate by "
		+ VACCINATION_LETHALITY + "% (" + state.getLethalityRate() + "%)");
	}
	
	private void executeCure(State state) {
		int cured = Math.max(0, Math.min(state.getInfected(), CURE_INFECTED));
		state.setSane(state.getSane() + cured);
		state.setInfected(state.getInfected() - cured);
		
		if (GAME_MESSAGES) System.out.println(state.getOwner().getDisplayName() + " cured " + cured + " infected");
	}
	
	private void executeQuarantine(State state) {
		int quarantined = Math.max(0, Math.min(state.getInfected(), QUARANTINE_INFECTION));
		state.setInfected(state.getInfected() - quarantined);
	
		if (GAME_MESSAGES) System.out.println(state.getOwner().getDisplayName() + " quarantined " + quarantined + " infected");
	}
	
	private void executeOpenBorders(State state) {
		state.setMigrationRate(Math.min(100, state.getMigrationRate() + OPEN_MIGRATION));
	
		if (GAME_MESSAGES) System.out.println(state.getOwner().getDisplayName() + " increased migration rate by " + OPEN_MIGRATION + "% (" + state.getMigrationRate() + "%)");
	}
	
	private void executeCloseBorders(State state) {
		state.setMigrationRate(Math.max(0, state.getMigrationRate() - CLOSE_MIGRATION));		

		if (GAME_MESSAGES) System.out.println(state.getOwner().getDisplayName() + " decreased migration rate by " + OPEN_MIGRATION + "% (" + state.getMigrationRate() + "%)");
	}
	
	private void executeBioterrorism(State state) {
		for (State s : states) {
			int infected = Math.min(s.getSane(), BIOTERRORISM_INFECTED);
			s.setSane(s.getSane() - infected);
			s.setInfected(s.getInfected() + infected);
		}
	
		if (GAME_MESSAGES) System.out.println(state.getOwner().getDisplayName() + " launched a bioweapon : +" + BIOTERRORISM_INFECTED + " global infected");
	}
	
	private void executeDissemination(State state) {
		for (State s : states) {
			s.setInfectionRate(s.getInfectionRate() + DISSEMINATION_INFECTION);
			s.setContagionRate(Math.min(100, s.getContagionRate() + DISSEMINATION_CONTAGION));
		}
		
		if (GAME_MESSAGES) System.out.println(state.getOwner().getDisplayName() + " increased global infection rate by " + DISSEMINATION_INFECTION + " and global contagion rate by " + DISSEMINATION_CONTAGION + "%");
	}
	
	private void executeWeaponization(State state) {
		for (State s : states) {
			s.setInfectionRate(s.getInfectionRate() + WEAPONIZATION_INFECTION);
			s.setLethalityRate(Math.min(100, s.getLethalityRate() + WEAPONIZATION_LETHALITY));
		}
	
		if (GAME_MESSAGES) System.out.println(state.getOwner().getDisplayName() + " increased global infection rate by " + WEAPONIZATION_INFECTION + " and global lethality rate by " + WEAPONIZATION_LETHALITY + "%");
	}
	
	private void executePacification(State state) {
		for (State s : states) {
			s.setInfectionRate(Math.max(0, s.getInfectionRate() - PACIFICATION_INFECTION));
			s.setContagionRate(Math.max(0, s.getContagionRate() - PACIFICATION_CONTAGION));
			s.setLethalityRate(Math.max(0, s.getLethalityRate() - PACIFICATION_LETHALITY));
		}
		
		if (GAME_MESSAGES) System.out.println(state.getOwner().getDisplayName() + " decreased global infection rate by " + PACIFICATION_INFECTION + ", global contation rate by " + PACIFICATION_CONTAGION + "% and global lethality rate by " + PACIFICATION_LETHALITY + "%");
	}
	
	private void executeWait(State state) {}
	
	private void mutation() {
		
		Random r = new Random();
		int mutationType = r.nextInt(3);
		
		for (State state : states) {
			
			if (mutationType == 0) {
				state.setInfectionRate(state.getInfectionRate() + MUTATION_INFECTION);				
			} else if (mutationType == 1) {
				state.setContagionRate(Math.min(100, state.getContagionRate() + MUTATION_CONTAGION));
			} else if (mutationType == 2) {
				state.setLethalityRate(Math.min(100, state.getLethalityRate() + MUTATION_LETHALITY));
			}
		}
				
		if (GAME_MESSAGES && mutationType == 0) System.out.println("Mutation : +" + MUTATION_INFECTION + " global infection rate");
		if (GAME_MESSAGES && mutationType == 1) System.out.println("Mutation : +" + MUTATION_CONTAGION + "% global contagion rate");
		if (GAME_MESSAGES && mutationType == 2) System.out.println("Mutation : +" + MUTATION_LETHALITY + "% global lethality rate");
	}
	
	private void reproduction() {
		
		if (round % BIRTH_ROUND == 0) {
			for (State state : states) {
				
				int saneBirths = Math.floorDiv(state.getSane(), 2);
				int infectedBirths = Math.floorDiv(state.getInfected(), 2);
				
				state.setSane(state.getSane() + saneBirths);
				state.setInfected(state.getInfected() + infectedBirths);
				
				if (GAME_MESSAGES && (saneBirths + infectedBirths > 0)) {
					System.out.println(state.getOwner().getDisplayName() + " : " + (saneBirths + infectedBirths) + " births (" +  saneBirths + " sane and " + infectedBirths + " infected)");
				}
			}
		}
	}
	
	private void migration() {
		
		int migrationSane = 0;
		int migrationInfected = 0;
		int migrationWeight = 0;
		
		// Emigration
		for (State state : states) {
			
			int stateMigrationSane = Math.min(state.getSane(), Math.floorDiv(state.getSane() * state.getMigrationRate(), 100));
			int stateMigrationInfected = Math.min(state.getSane(), Math.floorDiv(state.getInfected() * state.getMigrationRate(), 100));
			
			state.setSane(state.getSane() - stateMigrationSane);
			state.setInfected(state.getInfected() - stateMigrationInfected);

			migrationSane += stateMigrationSane;
			migrationInfected += stateMigrationInfected;
			migrationWeight += state.getMigrationRate();
			
			if (GAME_MESSAGES && (stateMigrationSane + stateMigrationInfected > 0)) System.out.println(state.getOwner().getDisplayName() + " : " + (stateMigrationSane + stateMigrationInfected) + " emigrated (" + stateMigrationSane + " sane, " + stateMigrationInfected + " infected)");
		}
		
		// Immigration
		for (State state : states) {
			
			int migrationRate = state.getMigrationRate();
			int migrationRatio = Math.floorDiv(migrationRate * 100, migrationWeight);
			int stateMigrationSane = Math.floorDiv(migrationSane * migrationRatio, 100);
			int stateMigrationInfected = Math.floorDiv(migrationInfected * migrationRatio, 100);
			
			state.setSane(state.getSane() + stateMigrationSane);
			state.setInfected(state.getInfected() + stateMigrationInfected);
			
			if (GAME_MESSAGES && (stateMigrationSane + stateMigrationInfected > 0)) System.out.println(state.getOwner().getDisplayName() + " : " + (stateMigrationSane + stateMigrationInfected) + " immigrated (" + stateMigrationSane + " sane, " + stateMigrationInfected + " infected)");
		}
	}
	
	private void infection() {
		
		for (State state : states) {
			
			int infections = Math.min(state.getSane(), state.getInfectionRate());
			
			state.setSane(state.getSane() - infections);
			state.setInfected(state.getInfected() + infections);
			
			if (GAME_MESSAGES && (infections > 0)) {
				System.out.println(state.getOwner().getDisplayName() + " : " + infections + " infection(s)");
			}
		}
	}
	
	private void contagion() {
		
		for (State state : states) {
			
			int contagions = Math.min(state.getSane(), Math.floorDiv(state.getInfected() * state.getContagionRate(), 100));
			
			state.setSane(state.getSane() - contagions);
			state.setInfected(state.getInfected() + contagions);
			
			if (GAME_MESSAGES && (contagions > 0)) {
				System.out.println(state.getOwner().getDisplayName() + " : " + contagions + " contagion(s)");
			}
		}
	}
	
	private void extinction() {
		
		for (State state : states) {
			
			int deaths = Math.min(state.getInfected(), Math.floorDiv(state.getInfected() * state.getLethalityRate(), 100));
			
			state.setInfected(state.getInfected() - deaths);
			state.setDead(state.getDead() + deaths);
			
			if (GAME_MESSAGES && (deaths > 0)) {
				System.out.println(state.getOwner().getDisplayName() + " : " + deaths + " death(s)");
			}
		}
	}
	
	private String generateArgs() {
		
		StringBuilder builder = new StringBuilder();
		//PlayerID_Sane_Infected_Dead_IR_CR_LR_MR
		for (State state : states) {
			builder.append(';');
			builder.append(state.getOwner().getId()).append('_');
			builder.append(state.getSane()).append('_');
			builder.append(state.getInfected()).append('_');
			builder.append(state.getDead()).append('_');
			builder.append(state.getInfectionRate()).append('_');
			builder.append(state.getContagionRate()).append('_');
			builder.append(state.getLethalityRate()).append('_');
			builder.append(state.getMigrationRate());
		}
		return builder.toString();
	}
	
	private void printResults() {
		
		List<Score> scores = new ArrayList<Score>();
		
		System.out.println("********** FINISH **********");
		
		for (Player player : players) {
			int sane = 0;
			int infected = 0;
			int dead = 0;
			
			for (State state : states) {
				if (player.equals(state.getOwner())) {
					sane += state.getSane();
					infected += state.getInfected();
					dead += state.getDead();
				}
			}
			
			scores.add(new Score(player, sane, infected, dead));
		}
		
		//sort descending
		Collections.sort(scores, Collections.reverseOrder());
		
		for (int i = 0; i < scores.size(); i++) {
			Score score = scores.get(i);
			System.out.println(i+1 + ". " + score.print());
		}
		
	}
	
}
