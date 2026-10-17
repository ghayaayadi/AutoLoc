package tn.esprit.autoloc.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule create(Vehicule entite) {
        if (entite.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return vehiculeRepository.save(entite);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule entite) {
        valider(entite);
        Vehicule existant = findById(id);
        existant.setImmatriculation(entite.getImmatriculation());
        existant.setMarque(entite.getMarque());
        existant.setModele(entite.getModele());
        existant.setCategorie(entite.getCategorie());
        existant.setTarifJournalier(entite.getTarifJournalier());
        existant.setStatut(entite.getStatut());
        existant.setAgence(entite.getAgence());
        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private void valider(Vehicule entite) {
        if (entite.getImmatriculation() == null || entite.getImmatriculation().isBlank()) {
            throw new IllegalArgumentException("L'immatriculation du véhicule est obligatoire");
        }
        if (entite.getMarque() == null || entite.getMarque().isBlank()) {
            throw new IllegalArgumentException("La marque du véhicule est obligatoire");
        }
        if (entite.getModele() == null || entite.getModele().isBlank()) {
            throw new IllegalArgumentException("Le modèle du véhicule est obligatoire");
        }
        if (entite.getTarifJournalier() == null
                || entite.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier doit être renseigné et positif ou nul");
        }
    }
}
