package com.aluracursos.foro_hub.domain.topic;

import com.aluracursos.foro_hub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public DatosRespuestaTopico registrarTopico(DatosRegistroTopico datosRegistroTopico) {
        validarDuplicateEntry(datosRegistroTopico);
        Topic topic = topicRepository.save(new Topic(datosRegistroTopico));
        return new DatosRespuestaTopico(topico);
    }

    public DatosRespuestaTopico detallarTopico(Long id) {
        validar(id);
        Topic topic = topicRepository.getReferenceById(id);
        return new DatosRespuestaTopico(topico);
    }

    public DatosRespuestaTopico actualizarTopico(Long id, DatosRegistroTopico datosRegistroTopico) {
        validar(id);
        Topic topic = topicRepository.getReferenceById(id);
        validarDuplicateEntry(datosRegistroTopico);
        topico.actualizarDatos(datosRegistroTopico);
        return new DatosRespuestaTopico(topico);
    }

    public void eliminarTopico(Long id) {
        validar(id);
        topicRepository.deleteById(id);
    }

    public void validar(Long id) {
        if (topicRepository.findById(id).isEmpty()) {
            throw new ValidacionDeIntegridad("Este id para el tópico no fue encontrado");
        }
    }

    private void validarDuplicateEntry(DatosRegistroTopico datosRegistroTopico) {
        if (topicRepository.existsByTitulo(datosRegistroTopico.titulo())) {
            throw new ValidacionDeIntegridad("Ya hay un tópico con ese título, modifique su título");
        }
        if (topicRepository.existsByMensaje(datosRegistroTopico.mensaje())) {
            throw new ValidacionDeIntegridad("Ya hay un tópico con ese mensaje, modifique su mensaje");
        }
    }
}
