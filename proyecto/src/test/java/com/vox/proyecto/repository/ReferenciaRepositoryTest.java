package com.vox.proyecto.repository;

import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.modelo.Referencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReferenciaRepositoryTest {

    @Mock
    private ReferenciaRepository referenciaRepository;

    @Mock
    private Publicacion mockPublicacion;

    @InjectMocks
    private ReferenciaRepositoryTest referenciaRepositoryTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByPublicacion() {
        Referencia ref1 = new Referencia();
        Referencia ref2 = new Referencia();
        List<Referencia> referencias = Arrays.asList(ref1, ref2);

        when(referenciaRepository.findByPublicacion(mockPublicacion)).thenReturn(referencias);

        List<Referencia> result = referenciaRepository.findByPublicacion(mockPublicacion);

        assertEquals(2, result.size(), "Debería haber dos referencias para la publicación");
        verify(referenciaRepository, times(1)).findByPublicacion(mockPublicacion);
    }

    @Test
    public void testCountByPublicacionAndAnonimoRef() {
        when(referenciaRepository.countByPublicacionAndAnonimoRef(mockPublicacion, true)).thenReturn(5L);

        long count = referenciaRepository.countByPublicacionAndAnonimoRef(mockPublicacion, true);

        assertEquals(5, count, "Debería haber 5 referencias anónimas para la publicación");
        verify(referenciaRepository, times(1)).countByPublicacionAndAnonimoRef(mockPublicacion, true);
    }
}
