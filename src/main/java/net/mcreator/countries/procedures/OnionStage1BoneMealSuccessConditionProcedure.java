package net.mcreator.countries.procedures;

public class OnionStage1BoneMealSuccessConditionProcedure {
	public static boolean execute() {
		if (Math.random() < 0.75) {
			return true;
		}
		return false;
	}
}
