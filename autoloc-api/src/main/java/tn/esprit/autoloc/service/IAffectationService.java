package tn.esprit.autoloc.service;

import java.time.LocalDate;
import tn.esprit.autoloc.dto.request.ReservationWithContratRequestDTO;
import tn.esprit.autoloc.dto.response.ContratDTO;
import tn.esprit.autoloc.dto.response.ReservationResponseDTO;
import tn.esprit.autoloc.dto.response.VehiculeDTO;

/** Services d'affectation (Atelier 7) : relient des entites existantes entre elles. */
public interface IAffectationService {

    ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom);

    VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement);

    VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation);

    ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin);

    ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO);
}
