package com.nhlstenden.amazonsimulatie.models;

interface RobotListener {

    /**
     * ============== HALLO DOCENT :) ==============
     * De Robot Taken implementeerden eerst Robot Listener.
     * Robot had toen ook nog normale addListener() en removeListener() functies.
     * De robot vertelde dan aan ELKE listener wanneer het zijn pad had bereikt.
     * Dit leidde alleen jammer genoeg tot bugs die uiteindelijk niet opgelost konden worden.
     * Robot heeft nu geen listener fields / functies meer,
     * maar de Robot Taken implementeren nog steeds RobotListener zodat er altijd
     * nog geprobeerd kan worden om die juiste manier wel weer te programmeren.
     */

    /**
     * Event that gets fired when this robot has finished its path
     */
    void onFinishedPath();
}
