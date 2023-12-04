# TODO

- Hacer README
- Hacer que admita NULL considerations en Incident en la db.
- Diseñar área comercial
- Añadir crud de tecnico a RRHH
- Realizar ciclo de vida de incidente

## Otros requerimientos

- [ ] El sistema debe permitir que el operador agregue un colchon de horas estimadas para la resolucion del problema, si el mismo es considerado complejo (Añadir a tipo de problema campo "complexity")

- [ ] El sistema debe permitir el alta de incidentes que contengan un conjunto de problemas de un mismo servicio. Dichos problemas deben estar relacionados

- [ ] El sistema debe dar la posibilidad de informar:

  - Quien fue el tecnico con mas incidentes resueltos en los ultimos N dias
  - Quien fue el tecnico con mas incidentes resueltos de una determinada especialidad en los ultimos N dias
  - [x] Quien fue el técnico que más rapido resolvio los incidentes

## Consideraciones

- [ ] Cada tipo de problema particular puede ser solucionado por una o varias especialidades
- [ ] Cada operador puede definir, optativamente, su tiempo estimado de resolucion por defecto por tipo de problema; el cual tendrá que ser menor al tiempo máximo de resolución definido para el tipo de problema.
- [x] Cada tecnico puede definir su medio preferido de notificación, los cuales pueden ser email o whatsapp.

## EntityManager

- Extraer de las entidades el entitymanager
