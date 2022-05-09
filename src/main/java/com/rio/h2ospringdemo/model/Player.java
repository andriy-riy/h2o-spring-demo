package com.rio.h2ospringdemo.model;

import java.time.LocalDate;

public record Player(
        String name,
        String fullName,
        LocalDate birthDate,
        Short age,
        Float heightCm,
        Float weightKgs,
        String nationality,
        Short overallRating,
        Short potential,
        Float valueEuro,
        Float wageEuro,
        PreferredFoot preferredFoot,
        Byte internationalReputation,
        Byte weakFoot,
        Byte skillMoves,
        String workRate,
        String bodyType,
        Float releaseClauseEuro,
        String clubTeam,
        Float clubRating,
        Position clubPosition,
        Float clubJerseyNumber,
        LocalDate clubJoinDate,
        Integer contractEndYear,
        String nationalTeam,
        Float nationalRating,
        Position nationalTeamPosition,
        Float nationalJerseyNumber,
        Short crossing,
        Short finishing,
        Short headingAccuracy,
        Short shortPassing,
        Short volleys,
        Short dribbling,
        Short curve,
        Short freekickAccuracy,
        Short longPassing,
        Short ballControl,
        Short acceleration,
        Short sprintSpeed,
        Short agility,
        Short reactions,
        Short balance,
        Short shotPower,
        Short jumping,
        Short stamina,
        Short strength,
        Short longShots,
        Short aggression,
        Short interceptions,
        Short positioning,
        Short vision,
        Short penalties,
        Short composure,
        Short marking,
        Short standingTackle,
        Short slidingTackle,
        Short GKDiving,
        Short GKHandling,
        Short GKKicking,
        Short GKPositioning,
        Short GKReflexes
) {

    public enum Position {
       /*_________________________________________*/
       /**/         LS,     ST,     RS,         /**/
       /**/      LF,        CF,        RF,      /**/
       /**/ LW,                             RW, /**/
       /**/        LAM,     CAM,   RAM,         /**/
       /**/ LM,     LCM,    CM,   RCM,     RM,  /**/
       /**/        LDM,     CDM,   RDM,         /**/
       /**/ LWB,                           RWB, /**/
       /**/    LB,   LCB,   CB,   RCB,   RB,    /**/
       /**/                                     /**/
       /*______________*/   GK,   /*______________*/

        SUB, RES
    }

    public enum PreferredFoot {
        RIGHT("Right"), LEFT("Left");

        private final String value;

        PreferredFoot(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
