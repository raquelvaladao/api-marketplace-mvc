package com.api.estudo.repositories;


import com.api.estudo.entities.Amizade;
import com.api.estudo.entities.Usuario;
import com.api.estudo.enums.StatusAmizade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmizadeRepository extends JpaRepository<Amizade, Long> {
    Amizade findByRemetente_IdAndDestinatario_Id(Long remetenteId, Long destinatarioId);
    Amizade findByDestinatario_IdAndRemetente_Id(Long destinatarioId, Long remetenteId);

    List<Amizade> findByRemetente_IdOrDestinatario_IdAndStatus
            (Long remetenteId, Long destinatarioId, StatusAmizade status);

}
