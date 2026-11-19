package tn.esprit.autoloc.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String ressource, Long id) {
        super(ressource + " introuvable avec l'identifiant " + id);
    }

    /** Ressource recherchee par un critere autre que l'id (nom/prenom, immatriculation, periode...). */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
