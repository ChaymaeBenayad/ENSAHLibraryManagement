package model;

public class CompteId {
	private static Utilisateur user = new Utilisateur();

	public static Utilisateur getUser() {
		return user;
	}

	public static void setUser(Utilisateur user) {
		CompteId.user = user;
	}

}
