package information;

import java.util.*;
import java.util.stream.Stream;

/**
 * La classe Information sert à contenir des d'un signal logique ou numérique.
 *
 * @author prou
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 * @author Pierrick CHOVELON
 * @author Mélanie CORRE
 */
public class Information<T> implements Iterable<T> {

    ArrayList<T> content;

    /**
     * pour construire une information vide
     */
    public Information() {
        this.content = new ArrayList<T>();
    }

    /**
     * pour construire à partir d'un tableau de T une information
     *
     * @param default_size la taille du tableau par défaut
     */
    public Information(int default_size) {
        this.content = new ArrayList<T>(default_size);
    }

    /**
     * pour construire à partir d'un tableau de T une information
     *
     * @param content le tableau d'élèments pour initialiser l'information
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
     * pour connaitre le nombre d'élèments d'une information
     *
     * @return le nombre d'élèments de l'information
     */
    public int nbElements() {
        return this.content.size();
    }

    /**
     * pour recuperer le stream des données d'une information
     *
     * @return le stream des données d'une information
     */
    /*
     public Stream<T> getStream() {
     return this.content.parallelStream();
     }
     */
    /**
     * pour renvoyer un tableau basique d'élèment d'une information
     *
     * @param array Le tableau a remplir avec les valeurs
     */
    //*
     public void toArray(T[] array) {
     this.content.toArray(array);
     }
     //*/
    /**
     * pour renvoyer un élèment d'une information
     *
     * @param i indice de l'élèmetn à retourné
     * @return le ieme élèment de l'information
     */
    public T iemeElement(int i) {
        return this.content.get(i);
    }

    /**
     * pour modifier le ième élèment d'une information
     *
     * @param i indice de l'élèmetn à modifier
     * @param v valeur à modifier
     */
    public void setIemeElement(int i, T v) {
        this.content.set(i, v);
    }

    /**
     * pour ajouter un élèment à la fin de l'information
     *
     * @param valeur l'élèment à rajouter
     */
    public void add(T valeur) {
        this.content.add(valeur);
    }

    /**
     * pour comparer l'information courante avec une autre information
     *
     * @param o l'information avec laquelle se comparer
     * @return "true" si les 2 informations contiennent les mêmes élèments aux
     * mêmes places; "false" dans les autres cas
     */
    @SuppressWarnings("unchecked")
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
     */
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
    public Iterator<T> iterator() {
        return content.iterator();
    }

}
