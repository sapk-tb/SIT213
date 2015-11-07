package information;

import java.util.*;

/**
 * La classe Information sert � contenir des d'un signal logique ou num�rique.
 *
 * @author prou
 * @author Antoine GIRARD
 * @author C�dric HERZOG
 * @author Pierrick CHOVELON
 * @author M�lanie CORRE
 * @param <T> Le format des donn�es dans l'information
 */
public class Information<T> implements Iterable<T> {

    ArrayList<T> content;

    /**
     * pour construire une information vide
     */
    public Information() {
        this.content = new ArrayList<>();
    }

    /**
     * pour construire � partir d'un tableau de T une information
     *
     * @param default_size la taille du tableau par d�faut
     */
    public Information(int default_size) {
        this.content = new ArrayList<>(default_size);
    }

    /**
     * pour construire un clone � partir d'une information de T une information
     *
     * @param information que l'on clone
     */
    public Information(Information<T> Inf) {
        this.content = (ArrayList<T>) Inf.content.clone();
    }

    /**
     * pour construire � partir d'un tableau de T une information
     *
     * @param content le tableau d'�l�ments pour initialiser l'information
     * construite
     */
    public Information(T[] content) {
        //this.content.add(Arrays.asList(content));
        //*
        this(content.length);
        int l = content.length;
        for (int i = 0; i < l; i++) {
            this.content.add(content[i]);
        }
        //*/
    }

    /**
     * pour connaitre le nombre d'�l�ments d'une information
     *
     * @return le nombre d'�l�ments de l'information
     */
    public int nbElements() {
        return this.content.size();
    }

    /*
     * pour recuperer le stream des donn�es d'une information
     *
     * @return le stream des donn�es d'une information
     */
    /*
     public Stream<T> getStream() {
     return this.content.parallelStream();
     }
     //*/
    /**
     * pour renvoyer un tableau basique d'�l�ments d'une information
     *
     * @param array Le tableau a remplir avec les valeurs
     */
    //*
    public void toArray(T[] array) {
        this.content.toArray(array);
    }

    //*/
    /**
     * pour renvoyer un �l�ment d'une information
     *
     * @param i indice de l'�l�ment � retourn�
     * @return le ieme �l�ment de l'information
     */
    public T iemeElement(int i) {
        return this.content.get(i);
    }

    /**
     * pour modifier le i�me �l�ment d'une information
     *
     * @param i indice de l'�l�ment � modifier
     * @param v valeur � modifier
     */
    public void setIemeElement(int i, T v) {
        this.content.set(i, v);
    }

    /**
     * pour ajouter un �l�ment � la fin de l'information
     *
     * @param valeur l'�l�ment � rajouter
     */
    public void add(T valeur) {
        this.content.add(valeur);
    }

    /**
     * pour ajouter un �l�ment � la fin de l'information
     *
     * @param i index
     * @param valeur l'�l�ment � rajouter
     */
    public void addAt(int i, T valeur) {
        this.content.add(i, valeur);
    }

    /**
     * pour comparer l'information courante avec une autre information
     *
     * @param o l'information � comparer
     * @return "true" si les 2 informations contiennent les m�mes �l�ments aux
     * m�mes places; "false" dans les autres cas
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Information)) {
            return false;
        }
        Information<T> information = (Information<T>) o;
        if (this.nbElements() != information.nbElements()) {
            return false;
        }
        for (int i = 0; i < this.nbElements(); i++) {
            if (!this.iemeElement(i).equals(information.iemeElement(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * pour afficher une information
     * @return s String, chaine cr��e
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.nbElements(); i++) {
            s += " " + this.iemeElement(i);
        }
        return s;
    }

    /**
     * pour utilisation du "for each"
     */
    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

}
