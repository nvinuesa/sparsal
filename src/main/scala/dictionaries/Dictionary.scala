package dictionaries

class Dictionary[T](val Θ: Seq[T]) {

  /**
    * Retrieve atom ω from the dictionary dict
    *
    * @param idx The index of the atom ω in Θ
    * @return The atom ω
    */
  def getAtom(idx: Int): T = {

    this.Θ(idx)
  }
}
