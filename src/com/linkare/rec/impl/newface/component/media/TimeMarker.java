package com.linkare.rec.impl.newface.component.media;

/**
 * Interface que define um comportamento para classes que se pretendam que
 * façam uma marcação de tempo.
 * @author bcatarino
 */
public interface TimeMarker {

    /**
     * Permite obter a marcação actual do TimeMarker.
     * (a desaparecer???)Se a implementação dos
     * métodos desta interface que alteram a posição do marcador for baseada
     * na posição actual do próprio objecto que implementa esta interface,
     * será necessário marcar este método como synchronized, para evitar que
     * seja lida uma posição incorrecta por entre os cálculos que irão definir
     * a próxima posição do marcador.
     * @return Posição actual do marcador.
     */
    int getCurrentPosition();

    /**
     * Devolve o valor máximo que o TimeMarker pode atingir, definido de acordo
     * com a métrica usada pela classe que implementa esta interface.
     * @return Valor máximo que o TimeMarker pode atingir.
     */
    int getMaximumPosition();

    //TODO como garantir a atomicidade da leitura e da escrita no marker?

    //TODO O Calculate deve receber o TimeSlider e "locká-lo" durante todo o método (synchronize ao marker).
    
    /**
     * Permite mover o TimeMarker para a nova posição.
     * @param newPosition
     */
    void setMarkerPosition(int newPosition);
}