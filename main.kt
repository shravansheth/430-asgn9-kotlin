interface ExprC

class NumC(val n: Float) : ExprC
class StrC(val s: String) : ExprC
class IfC(val a: ExprC, val b: ExprC, val c: ExprC) : ExprC
class IdC(val name: String) : ExprC
class AppC(val func: ExprC, val arg: List<ExprC>) : ExprC
class LamC(val arg: List<String>, val body: ExprC) : ExprC

class Bind(val name: String, val value: Value)
//class Env(val bindings: List<Bind>)
typealias Env = List<Bind>

interface Value 

class NumV(val n: Float) : Value
class StrV(val str: String) : Value
class BoolV(val bool : Boolean) : Value
class CloV (val args : List<String>, val body : ExprC, val env : Env) : Value
class PrimOpV(val sym : String) : Value

val topEnv = listOf(
    Bind("true", BoolV(true)),
    Bind("false", BoolV(false)),
    Bind("+", PrimOpV("+")),
    Bind("+", PrimOpV("+")),
    Bind("-", PrimOpV("-")),
    Bind("/", PrimOpV("/")),
    Bind("*", PrimOpV("*")),
    Bind("<=", PrimOpV("<=")),
    Bind("equal?", PrimOpV("equal?")),    
  )

fun lookup(f: String, env: Env) : Value
{
  when {
    env.isEmpty() -> throw Exception("OAZO: Couldn't find ${f} in Environment\n")
    env[0].name.equals(f) -> return env[0].value

  }
  
  return NumV(0.0f)
}

fun interp(exp: ExprC, env: Env) : Value {
  fun extendEnv(symbols : List<String>, arg: List<ExprC>, fenv: Env) : Env
  {
    extendEnv(symbols, arg, fenv)
  }

  when {
    exp is NumC -> return NumV(exp.n)
    exp is StrC -> return StrV(exp.s)
    exp is IdC -> return lookup(exp.name, env)
    exp is AppC -> {
      val fValue = interp(exp.func, env)
      when {
        fValue is PrimOpV -> return interpPrims(fValue.sym, 
          listOf(
            interp(exp.arg[0], env),
            interp(exp.arg[1], env)
          )
        )
        fValue is CloV -> {
          if(fValue.args.size == exp.arg.size) {
            interp(fValue.body, extendEnv(fValue.args, exp.arg, fValue.env))
          } else throw Exception("OAZO: invalid matching arguments in anon")
        } 
        else -> throw Exception("OAZO: Not implemented")
      }
    }
    else -> throw Exception("OAZO: Not implemented")
  }
}

fun interpPrims(op: String, args: List<Value>) : Value {
  if(args.isEmpty()) throw Exception("OAZO: NOT ENOUGH ARGUMENTS FOR PRIMOP ${op}\n")
  val left = args[0]
  val right = args[1]
  if(op.equals("error") && left is StrV) throw Exception("OAZO: User thrown error: ${left.str}\n")
  when {
    left is NumV &&  right is NumV && op.equals("+") -> return NumV(left.n + right.n)
    left is NumV &&  right is NumV && op.equals("-") -> return NumV(left.n - right.n)
    left is NumV &&  right is NumV && op.equals("*") -> return NumV(left.n * right.n)
    left is NumV &&  right is NumV && op.equals("/") -> {
      if (right.n.equals(0)) throw Exception("OAZO: Division by zero attempted\n")
      return NumV(left.n / right.n)}
    op.equals("equal?") -> return BoolV(left.equals(right))
    left is NumV &&  right is NumV && op.equals("<=") -> return BoolV(left.n <= right.n)
    else -> throw Exception("OAZO: Invalid Primitive Operation ${op}\n")
  }
}

fun parse(s: String) {

}

fun serialize(value: Value) : String {
  when {
    value is NumV -> return "${value.n}" 
    else -> throw Exception("OAZO: Not implemented")
  }
}

fun main(args: Array<String>) {
  val obj = NumC(10.0f)
  assert(serialize(interp(obj, topEnv)).equals("10"))
  assert(-1 > 0)
}