package edu.ntnu.idi.stud.team10.sparesti.util;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.service.*;

@Component
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {

  private final BadgeService badgeService;
  private final UserBadgeService userBadgeService;
  private final UserService userService;
  private final ConsumptionChallengeService consumptionChallengeService;
  private final UserChallengeService userChallengeService;
  private final UserRepository userRepository;
  private final SavingTipService savingTipService;

  public DataLoader(
      BadgeService badgeService,
      UserBadgeService userBadgeService,
      UserService userService,
      ConsumptionChallengeService consumptionChallengeService,
      UserChallengeService userChallengeService,
      UserRepository userRepository,
      SavingTipService savingTipService) {
    this.badgeService = badgeService;
    this.userBadgeService = userBadgeService;
    this.userService = userService;
    this.consumptionChallengeService = consumptionChallengeService;
    this.userChallengeService = userChallengeService;
    this.userRepository = userRepository;
    this.savingTipService = savingTipService;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    initialize();
  }

  private void resetBadges() {
    List<Badge> allBadges = badgeService.getAllBadges();
    for (Badge badge : allBadges) {
      badgeService.deleteBadgeById(badge.getId());
    }
    badgeService.deleteAllBadges(); // Implement this method in BadgeService to delete all badges
  }

  private void initialize() {
    createBadges();
    createSavingTips(); // works only if DB is empty

    try {
      userService.getUserByEmail("admin@admin");
    } catch (NotFoundException e) {
      // User not found, proceed with creating the admin user
      // The admin is created normally, then elevated to admin status manually
      UserDto adminUser = new UserDto();
      adminUser.setEmail("admin@admin");
      adminUser.setPassword("password");
      adminUser.setProfilePictureUrl(
          "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/09663791-e23b-427b-b8d4-a341664f4f0a_amongus.png");
      userService.addUser(adminUser);
      userRepository
          .findByEmail("admin@admin")
          .ifPresent(
              user -> {
                user.setRole("ADMIN");
                userRepository.save(user);
              });
    }

    Long adminUserId = userService.getUserByEmail("admin@admin").getId();

    BadgeDto loginBadge = badgeService.getBadgeByName("På god vei!");

    userBadgeService.giveUserBadge(adminUserId, loginBadge.getId());

    if (!userChallengeService.getSortedChallengesByUser(adminUserId).isEmpty()) {
      return;
    }

    ConsumptionChallenge challenge1 = new ConsumptionChallenge();
    challenge1.setTimeInterval(TimeInterval.valueOf("DAILY"));
    challenge1.setDifficultyLevel(DifficultyLevel.valueOf("EASY"));
    challenge1.setTitle("Spar 100 kr på kaffe");
    challenge1.setDescription("Ikke kjøp kaffe i dag og spar 10 kr");
    challenge1.setMediaUrl("☕");
    challenge1.setTargetAmount(100);
    challenge1.setProductCategory("kaffe");
    challenge1.setReductionPercentage(15.0);

    // Set challenge properties
    consumptionChallengeService.createChallenge(challenge1);
    userChallengeService.addChallengeToUser(adminUserId, challenge1.getId());

    ConsumptionChallenge challenge2 = new ConsumptionChallenge();
    challenge2.setTimeInterval(TimeInterval.valueOf("WEEKLY"));
    challenge2.setDifficultyLevel(DifficultyLevel.valueOf("MEDIUM"));
    challenge2.setTitle("Spar 100 kr på Red Bull");
    challenge2.setDescription("Ikke kjøp Red Bull i dag og spar 10 kr");
    challenge2.setMediaUrl("🧃");
    challenge2.setTargetAmount(100);
    challenge2.setProductCategory("Red Bull");
    challenge2.setReductionPercentage(15.0);

    consumptionChallengeService.createChallenge(challenge2);
    userChallengeService.addChallengeToUser(adminUserId, challenge2.getId());
  }

  private void createBadge(String name, String description, String imageUrl) {
    BadgeDto badgeDto = new BadgeDto();
    badgeDto.setName(name);
    badgeDto.setDescription(description);
    badgeDto.setImageUrl(imageUrl);
    badgeService.createBadge(badgeDto);
  }

  private void createBadges() {
    if (!badgeService.getAllBadges().isEmpty()) {
      return;
    }
    createBadge(
            "Sparesti",
            "Godt jobbet for å ha spart innenfor månedtlig budsjett!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/badge_pig_cropped.png");
    createBadge(
            "Red Bull",
            "Wow du har spart 100 kr på Red Bull denne uken!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/badge_cropped_red_bull.png");
    createBadge(
            "Kaffe",
            "Supert! Du har spart 150 kr på kaffe denne uken!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/badge_cropped_coffee.png");
    createBadge(
            "På god vei!",
            "Du er online!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/0-challenges.png\n");
    createBadge(
            "3 Utfordringer!",
            "Du har fullført 3 utfordringer!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/3-challenges.png\n");
    createBadge(
            "10 Utfordringer!",
            "Du har fullført 10 utfordringer!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/10-challenges.png\n");
    createBadge(
            "15 Utfordringer!",
            "Du har fullført 15 utfordringer!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/15-challenges.png\n");
    createBadge(
            "Bank ID",
            "Sikkerhetførst!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/bank_id.png\n");
    createBadge(
            "Kiwi",
            "Kiwi-mesteren! Du har spart 400 kr på ukeshandel hos Kiwi!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/kiwi.png\n");
    createBadge(
            "Rema 1001",
            "Du elsker Rema! Du har spart 300 kr på ukeshandel hos Rema 1001!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/rema_1001.png\n");
    createBadge(
            "Spar",
            "Spar, du har gjort en god deal! Du har spart 350 kr på ukeshandel hos Spar!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/spar.png\n");
    createBadge(
            "Bunin",
            "Bunnpris! Du har spart 500 kr på ukeshandel hos Bunin!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/bunin.png\n");
    createBadge(
            "Budsjett",
            "Dæven, du har stålkontroll på budsjettet!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/budget.png\n");
    createBadge(
            "Månedtlig utfordring",
            "Kjempet bra! Du har fullført en månedtlig utfordring!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/calendar.png\n");
    createBadge(
            "Klær",
            "Godt jobbet! Du har spart 400 kr på klær denne måneden!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/clothes.png\n");
    createBadge(
            "Sparemål oppnådd",
            "Du er best! Du har fullført et sparemål!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/completing_saving_goal.png\n");
    createBadge(
            "Profilbilde",
            "Du ser bra ut! Du har endret profilbildet ditt!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/profile_picture.png\n");
    createBadge(
            "Dagligvare handel",
            "Fortsett sånn! Du har spart 300 kr på dagligvarehandel denne uken!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/shopping_cart.png\n");
    createBadge(
            "Luftballong klikkeren!",
            "Trenger du tips til å spare penger?",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/red_airballoon.png\n");
    createBadge(
            "Delt sparemål",
            "Sharing is caring! Du har delt et sparemål med andre brukere!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/shared_goal.png\n");
    createBadge(
            "Husk å betal regninga!",
            "Du har kontroll! Du har lagt til en regning i appen!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/receipt.png\n");
    createBadge(
            "Transport",
            "Grønt miljø! Du har spart 200 kr på transport denne måneden!",
            "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/transportation.png\n");
    createBadge(
        "Sparemål nybegynner",
        "Du laget et sparemål!",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/create_saving_goal.png\n");
    createBadge(
        "Utfordrings nybegynner",
        "Du laget en utfordring!",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/create_challenge.png\n");
  }

  private void createSavingTips() {
    if (savingTipService.noSavingTips()) {
      List<String> savingTips =
          List.of(
              "Visste du? Å slå av lysene når du forlater et rom kan spare deg for omtrent 6 % på strømregningen hver måned. 💡",
              "Miljøtips: Ved å senke termostaten med bare én grad om vinteren kan du redusere oppvarmingsregningen med opptil 8 %. ❄️",
              "Budsjetteringsfakta: Personer som sporer utgiftene sine kan spare opptil 20 % mer enn de som ikke gjør det. 📊",
              "Finansiell visdom: Å betale ned gjelden med høyest rente først kan spare deg for hundrevis i renteutgifter. 💳",
              "Smart shopping: Å kjøpe ikke-bedervelige varer i bulk kan spare deg for opptil 50 % på nødvendige husholdningsartikler. 🛒",
              "Visste du? Å trekke ut støpselet på elektronikk når de ikke er i bruk, kan spare deg for opptil 1 000 kr i året på energikostnader. 🔌",
              "Matlagingstips: Å lage mat hjemme fire dager i uken kan spare deg for over 15 000 kr årlig sammenlignet med å spise ute. 🍽️",
              "Transportfakta: Regelmessig vedlikehold av bilen kan forbedre drivstoffeffektiviteten og spare deg for penger på bensin. 🚗",
              "Energisparetips: Å vaske klær i kaldt vann kan spare opptil 90 % av energien brukt per vask. 🧺",
              "Sparestrategi: Å sette opp automatiske overføringer til sparekontoer kan hjelpe deg med å spare uten å tenke på det. 🏦",
              "Visste du? Å ta med egen kaffe på jobb kan spare deg for over 10 000 kr per år. ☕",
              "Miljøtips: Å bruke en gjenbrukbar vannflaske kan spare deg for opptil 2 600 kr i året sammenlignet med å kjøpe flaskevann. 🍶",
              "Budsjetteringstips: Å gjennomgå abonnementstjenester årlig kan hjelpe deg med å unngå å betale for noe du ikke bruker. 📝",
              "Smart shopping: Å sjekke priser på nettet før kjøp kan føre til betydelige besparelser. 💻",
              "Bærekraftig livsstil: Å plante en hage kan redusere matvareutgiftene dine ved å dyrke dine egne grønnsaker og urter. 🌱",
              "Reisetips: Å bestille flybilletter på en tirsdag kan ofte resultere i lavere priser sammenlignet med andre dager. ✈️",
              "Finansiell helse: Å jevnlig sjekke kredittscoren din kan hjelpe med å forebygge svindel og forbedre dine finansielle muligheter. 📈",
              "Gjenbrukstips: Å selge unna ting du ikke bruker er en god måte å få mer penger inn på kontoen! 🔄",
              "Energisparende fakta: LED-pærer bruker minst 75 % mindre energi, og varer 25 ganger lenger, enn glødepærer. 💡",
              "Vannsparetips: Å reparere en lekk kran kan spare opptil 37 liter vann per dag. 💧",
              "Sparemotivasjon: Å sette kortsiktige økonomiske mål kan gjøre prosessen med å spare penger mer håndterbar og givende. 🎯",
              "Gjenbrukstips: Du kan spare en del penger dersom du kjøper brukt gjennom finn.no, Facebook marketplace, Tise, Letgo osv. 📦",
              "Matlagingstips: Kast mindre mat! Spis alt du kjøper og bruk restene i stedet for å kaste dem. Slik får du ned matbudsjettet. 🥗",
              "Sparetips: Bruk mindre enn du tjener. 🐖 Elles kommer jeg og finner deg.",
              "Sparetips: Det er best å handle dagligvarer for en hel uke. Jo flere ganger du er innom butikken, jo oftere blir fristelsen for impulskjøp. 🛍️",
              "Sparetips: Det er lurt å handle i butikken når man ikke er sulten. Sannsynligheten for impulskjøp er større dersom du er sulten! 🥖",
              "Sparetips: Prøv å ha èn eller flere kostnadsfrie dager i uken. Det er enklere enn du tror, og du vil spare mye! 🗓️",
              "Økonomitips: Automatiser sparepengene dine til en høyrentekonto for å maksimere avkastningen. 📊",
              "Miljøvennlig tips: Bruk sykkel eller gå til jobb minst to dager i uken for å redusere transportkostnader og miljøpåvirkning. 🚴",
              "Smart forbruk: Unngå impulskjøp ved alltid å vente 24 timer før du kjøper varer du ikke trenger umiddelbart. ⏳",
              "Shoppingtips: Benytt deg av sesongsalg for å kjøpe klær og elektronikk til sterkt reduserte priser. 🎉",
              "Hjemmetips: Isoler vinduer og dører for å redusere varmetap og spare på oppvarmingskostnadene. 🏠",
              "Kostholdstips: Planlegg ukens måltider på forhånd og kjøp kun det du trenger, slik reduserer du matsvinn og sparer penger. 📅",
              "Økonomitips: Betal alltid regningene dine i tide for å unngå forsinkelsesgebyrer. 🧾",
              "Reisetips: Sammenlign priser fra flere reisenettsteder før du bestiller ferie for å finne de beste tilbudene. 🌍",
              "Strømsparing: Slå av og trekk ut ladeapparater som ikke er i bruk for å spare strøm. 🔌",
              "Underholdningstips: Utbytt dyre TV-abonnementer med rimeligere streamingtjenester eller frie alternativer. 📺",
              "Shoppingtips: Bruk lojalitetskort og apper som tilbyr rabatter og bonuser. 💳",
              "Vannsparetips: Installer spare dusjhoder for å redusere vannforbruket og spare på vannregningen. 🚿",
              "Sparetips: Investér i kvalitetsprodukter som varer lenger i stedet for billige alternativer som må erstattes ofte. 🛍️",
              "Husholdningstips: Rengjør eller bytt ut filtrene i varme- og luftanlegg regelmessig for bedre effektivitet. 🌬️",
              "Sparetips for barn: Lær barna om penger ved å gi dem et ukentlig budsjett for småutgifter. 👶",
              "Helseøkonomi: Prioriter regelmessige helsesjekker for å forebygge dyrere behandlinger i fremtiden. 🏥",
              "Investeringstips: Start tidlig med å sette av en liten sum i aksjemarkedet for å dra nytte av renters rente. 📈",
              "Energisparetips: Bytt til LED-lys i hele hjemmet for å kutte ned på strømregningen. 💡",
              "Matlagingstips: Bruk en trykkoker for å spare både tid og energi når du lager mat. 🍲",
              "Sparetips: Bruk denne appen og spar milliarder med budsjetteringssystemet vårt! 😃",
              "Sparetips: Lag sparemål på appen for å spare opp for noe konkret! \uD83C\uDF91");

      for (String tip : savingTips) {
        savingTipService.createSavingTip(tip);
      }
    }
  }
}
