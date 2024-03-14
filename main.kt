interface ExprC
{

}

class NumC(val test: Int = 0) : ExprC
{

}

class Env
{

}

class Value 
{

}


fun interp(exp: ExprC, env: Env) {
  when {
    exp is NumC && exp.test == 10 -> println("1 " + exp.test)
    exp is NumC && exp.test == 13 -> println("2 " + exp.test)
    else -> println("3")
  }
}

fun parse(s: String) {

}

fun serialize(value: Value) {

}

fun main(args: Array<String>) {
  println("Hello World!")
  
  val obj = NumC(10)
  interp(obj, Env())
}