package edu.ntnu.idi.stud.team10.sparesti.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ntnu.idi.stud.team10.sparesti.enums.OccupationStatus;

public class ChallengeTemplates {

  Map<OccupationStatus, List<String[]>> challengeMap;

  public ChallengeTemplates() {
    challengeMap = new HashMap<>();

    challengeMap.put(
        OccupationStatus.STUDENT,
        Arrays.asList(
            new String[] {
              "Niste-konkurranse", "Spar penger ved å lage niste i stedet for å kjøpe lunsj ute."
            },
            new String[] {
              "Energialternativer", "Bytt ut energidrikker med kaffe eller te for å spare penger."
            },
            new String[] {"Kaffestopp", "Ta med kaffe hjemmefra i stedet for å kjøpe på skolen."},
            new String[] {"Alkoholfritt", "Spar penger ved å kutte ned på alkohol."},
            new String[] {"Klessparing", "Kjøp klær på salg eller brukt for å spare penger."},
            new String[] {"Sykkeltur", "Spar penger på transport ved å sykle til skolen."}));

    challengeMap.put(
        OccupationStatus.EMPLOYED,
        Arrays.asList(
            new String[] {
              "Lunsjpaus-sparing", "Bring med mat fra hjemme til jobben i stedet for å kjøpe det."
            },
            new String[] {
              "Kommunikasjonskostnad",
              "Spar penger på mobilabonnementet ved å velge en billigere plan."
            },
            new String[] {
              "Frokostfri", "Spar penger ved å lage frokost hjemme i stedet for å kjøpe på jobben."
            },
            new String[] {"Alkoholfritt", "Spar penger ved å kutte ned på alkohol."},
            new String[] {"Kontor-kaffe", "Ta med kaffe hjemmefra i stedet for å kjøpe på jobben."},
            new String[] {"Sykkeltur", "Spar penger på transport ved å sykle til jobben."}));

    challengeMap.put(
        OccupationStatus.UNEMPLOYED,
        Arrays.asList(
            new String[] {
              "Matbudsjett", "Spar penger på mat ved å lage mat hjemme i stedet for å spise ute."
            },
            new String[] {"Alkoholfri uke", "Spar penger ved å kutte ned på alkohol i en uke."},
            new String[] {"Klessparing", "Kjøp klær på salg eller brukt for å spare penger."},
            new String[] {
              "Sykkeltur", "Spar penger på transport ved å sykle i stedet for å kjøre bil."
            },
            new String[] {
              "Hjemmeunderholdning",
              "Spar penger på underholdning ved å finne gratis eller billige alternativer."
            },
            new String[] {
              "Energidrikker", "Bytt ut energidrikker med kaffe eller te for å spare " + "penger."
            }));

    challengeMap.put(
        OccupationStatus.RETIRED,
        Arrays.asList(
            new String[] {"Gambler", "Spar penger ved å kutte ned på gambling."},
            new String[] {"Alkoholfri uke", "Spar penger ved å kutte ned på alkohol."},
            new String[] {"Klessparing", "Kjøp klær på salg eller brukt for å spare penger."},
            new String[] {
              "Sykkeltur", "Spar penger på transport ved å sykle i stedet for å kjøre bil."
            },
            new String[] {
              "Hjemmeunderholdning",
              "Spar penger på underholdning ved å finne gratis eller billige alternativer."
            }));
  }

  public Map<OccupationStatus, List<String[]>> getChallengeMap() {
    return challengeMap;
  }
}
