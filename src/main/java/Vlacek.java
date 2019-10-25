import java.util.LinkedList;
import java.util.List;

public class Vlacek {

    private Vagonek lokomotiva = new Vagonek(VagonekType.LOKOMOTIVA);
    private Vagonek posledni = new Vagonek(VagonekType.POSTOVNI);
    private int delka = 2;

    public Vlacek(){
        lokomotiva.setNasledujici(posledni);
        lokomotiva.setUmisteni(1);
        posledni.setPredchozi(lokomotiva);
        posledni.setUmisteni(2);
    }

    /**
     * Přidávejte vagonky do vlaku
     * Podmínka je že vagonek první třídy musí být vždy řazen za předchozí vagonek toho typu, pokud žádný takový není je řazen rovnou za lokomotivu
     * vagonek 2 třídy musí být vždy řazen až za poslední vagonek třídy první
     * Poštovní vagonek musí být vždy poslední vagonek lokomotivy
     * Při vkládání vagonku nezapomeňte vagonku přiřadit danou pozici ve vlaku
     * !!!!!!! POZOR !!!!!! pokud přidáváte vagonek jinak než na konec vlaku musíte všem následujícím vagonkům zvýšit jejich umístění - doporučuji si pro tento účel vytvořit privátní metodu
     * @param type
     */
    public void pridatVagonek(VagonekType type) {
        Vagonek x = new Vagonek(type);

        switch (type)
        {
            case PRVNI_TRIDA:

                System.out.println("pridavam prvni tridu");
                x.setPredchozi(lokomotiva);
                x.setNasledujici(lokomotiva.getNasledujici());
                lokomotiva.getNasledujici().setPredchozi(x);
                lokomotiva.setNasledujici(x);

                setUmisteni();

                delka ++;
                break;
            case DRUHA_TRIDA:


                System.out.println("pridavam druhou tridu");

                x.setNasledujici(posledni);
                x.setPredchozi(posledni.getPredchozi());
                posledni.getPredchozi().setNasledujici(x);
                posledni.setPredchozi(x);
                setUmisteni();
                delka ++;
                break;


            case JIDELNI:

                pridatJidelniVagonek();
                setUmisteni();
                delka ++;


                break;
        }
    }


    public void setUmisteni(){
        Vagonek zaLokomotivou = lokomotiva.getNasledujici();
        for (int i = 0; i <delka-1 ; i++) {
            zaLokomotivou.setUmisteni(zaLokomotivou.getPredchozi().getUmisteni() + 1);
            zaLokomotivou = zaLokomotivou.getNasledujici();

        }



    }

    public Vagonek getVagonekByIndex(int index) {
        int i = 1;
        Vagonek atIndex = lokomotiva;
        while(i < index) {
            atIndex = atIndex.getNasledujici();
            i++;
        }
        return atIndex;
    }


    /**
     * Touto metodou si můžete vrátit poslední vagonek daného typu
     * Pokud tedy budu chtít vrátit vagonek typu lokomotiva dostanu hned první vagonek
     * @param type
     * @return
     */
    public Vagonek getLastVagonekByType(VagonekType type) {
        int delka = getDelka();
        System.out.println(delka);



        switch (type){

            case PRVNI_TRIDA:
                Vagonek vratit = najdiPosledniPrvniTridy();

                return vratit;

            case DRUHA_TRIDA:

        }

        return null;
    }

    public Vagonek najdiPosledniPrvniTridy(){

        int i = 2;

        while (getVagonekByIndex(i).getType() == VagonekType.PRVNI_TRIDA) {

            i++;
        }
        i--;
        return getVagonekByIndex(i);

    }

    /**
     * Tato funkce přidá jídelní vagonek za poslední vagonek první třídy, pokud jídelní vagonek za vagonkem první třídy již existuje
     * tak se další vagonek přidá nejblíže středu vagonků druhé třídy
     * tzn: pokud budu mít č osobních vagonků tak zařadím jídelní vagonek za 2 osobní vagónek
     * pokud budu mít osobních vagonků 5 zařadím jídelní vagonek za 3 osobní vagonek
     */
    public void pridatJidelniVagonek() {
        Vagonek jidelni = new Vagonek(VagonekType.JIDELNI);

        for (int i = 0; i < delka; i++) {

            if (getVagonekByIndex(i).getType() == VagonekType.JIDELNI){
                System.out.println("tohle je druhy");
                int x = getDelkaByType(VagonekType.DRUHA_TRIDA);
                x= x/2;

                System.out.println(x);
                Vagonek pomocny = posledni;

                for (int j = 0; j < x; j++) {
                    pomocny = pomocny.getPredchozi();
                }


                pomocny.getPredchozi().setNasledujici(jidelni);
                jidelni.setNasledujici(pomocny);
                jidelni.setPredchozi(pomocny.getPredchozi());
                pomocny.setPredchozi(jidelni);

                setUmisteni();
                delka ++;
                System.out.println("pridal jsem druhy jidelni");

                return;
            }

        }

        System.out.println("sssss");


        int i = 2;
        while (getVagonekByIndex(i).getType() == VagonekType.PRVNI_TRIDA) {
            i++;
            if ( i == 10000){ break;
            }
        }
        Vagonek x = getVagonekByIndex(i);
        x.getPredchozi().setNasledujici(jidelni);
        jidelni.setNasledujici(x);
        jidelni.setPredchozi(x.getPredchozi());
        x.setPredchozi(jidelni);


    }

    /**
     * Funkce vrátí počet vagonků daného typu
     * Dobré využití se najde v metodě @method(addJidelniVagonek)
     * @param type
     * @return
     */
    public int getDelkaByType(VagonekType type) {

        switch (type)
        {
            case PRVNI_TRIDA:

                int i = 2;
                while (getVagonekByIndex(i).getType() == VagonekType.PRVNI_TRIDA ){
                    i++;
                    if (i == 1000){
                        break;
                    }
                }
               i = i-2;
                return i;

            case DRUHA_TRIDA:

                int vysledek = 0 ;

                for (int j = 0; j <delka ; j++) {

                    if (getVagonekByIndex(j).getType() == VagonekType.DRUHA_TRIDA){
                        System.out.println("funguje to ?");
                        vysledek ++;
                    }

                }
                return vysledek;


            case LOKOMOTIVA:
                return 1;


        }

        return 0;
    }

    /**
     * Hledejte jidelni vagonky
     * @return
     */
    public List<Vagonek> getJidelniVozy() {
        List<Vagonek> jidelniVozy = new LinkedList<>();

        int help = 1;

        for (int i = 0; i < delka; i++) {

            if (getVagonekByIndex(help).getType() == VagonekType.JIDELNI){

                jidelniVozy.add(getVagonekByIndex(help));

            }

            help ++;
        }

        return jidelniVozy;
    }

    /**
     * Odebere poslední vagonek daného typu
     * !!!! POZOR !!!!! pokud odebíráme z prostředku vlaku musíme zbývající vagonky projít a snížit jejich umístění ve vlaku
     * @param type
     */
    public void odebratPosledniVagonekByType(VagonekType type) {

        switch (type){

            case JIDELNI:


                    int i = posledni.getUmisteni();

                while (  getVagonekByIndex(i).getType() != VagonekType.JIDELNI ){
                    System.out.println("sssssskrrrrrrrr");
                    i--;

                    if (i < 0){
                        break;
                    }

                }
                System.out.println(i);
                if (getVagonekByIndex(i).getType() == VagonekType.JIDELNI){

                    Vagonek jidenlni = getVagonekByIndex(i);
                    jidenlni.getNasledujici().setPredchozi(jidenlni.getPredchozi());
                    jidenlni.getPredchozi().setNasledujici(jidenlni.getNasledujici());


                    setUmisteni();
                    delka--;

                }
                System.out.println(i);


break;

        }


    }

    public int getDelka() {
        return delka;
    }
}
