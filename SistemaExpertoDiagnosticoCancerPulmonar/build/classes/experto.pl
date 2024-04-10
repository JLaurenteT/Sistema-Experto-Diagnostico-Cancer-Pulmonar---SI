% Enfermedades
enfermedad(cancer_pulmonar).

% Factores de riesgo
factor_riesgo(tabaquismo).
factor_riesgo(exposicion_radon).
factor_riesgo(asbesto).
factor_riesgo(contaminacion_aire).

% Síntomas
sintoma(tos_persistente).
sintoma(dificultad_respiratoria).
sintoma(perdida_peso).
sintoma(dolor_toracico).

% Diagnóstico
diagnostico(radiografia_torax).
diagnostico(tomografia_computarizada).
diagnostico(resonancia_magnetica).
diagnostico(biopsia).
diagnostico(pruebas_moleculares).

% Tratamientos
tratamiento(cirugia).
tratamiento(radioterapia).
tratamiento(quimioterapia).
tratamiento(terapias_dirigidas).

% Relaciones
causa(tabaquismo, cancer_pulmonar).
causa(exposicion_radon, cancer_pulmonar).
causa(asbesto, cancer_pulmonar).
causa(contaminacion_aire, cancer_pulmonar).

presenta_sintoma(cancer_pulmonar, tos_persistente).
presenta_sintoma(cancer_pulmonar, dificultad_respiratoria).
presenta_sintoma(cancer_pulmonar, perdida_peso).
presenta_sintoma(cancer_pulmonar, dolor_toracico).

requiere_diagnostico(cancer_pulmonar, radiografia_torax).
requiere_diagnostico(cancer_pulmonar, tomografia_computarizada).
requiere_diagnostico(cancer_pulmonar, resonancia_magnetica).
requiere_diagnostico(cancer_pulmonar, biopsia).
requiere_diagnostico(cancer_pulmonar, pruebas_moleculares).

recibe_tratamiento(cancer_pulmonar, cirugia).
recibe_tratamiento(cancer_pulmonar, radioterapia).
recibe_tratamiento(cancer_pulmonar, quimioterapia).
recibe_tratamiento(cancer_pulmonar, terapias_dirigidas).

% Prevención
prevencion(no_fumar).
prevencion(reducir_exposicion_carcinogenos).
prevencion(mejorar_calidad_aire).



% Regla para diagnosticar cáncer de pulmón
diagnostica_cancer_pulmonar(Paciente) :-
    presenta_sintoma(cancer_pulmonar, Sintoma),
    verify_sintoma(Paciente, Sintoma),
    write('El paciente presenta los siguientes síntomas: '), 
    write(Sintoma), nl,
    write('Realizar los siguientes exámenes de diagnóstico: '), nl,
    diagnosticos_requeridos(cancer_pulmonar, Examenes),
    write(Examenes), nl,
    write('Posible diagnóstico: Cáncer de pulmón').

% Regla para verificar si el paciente presenta un síntoma
verify_sintoma(_, Sintoma) :-
    write('El paciente presenta el síntoma: '), 
    write(Sintoma), nl,
    write('¿Es correcto? (si/no) '), 
    read(Respuesta),
    Respuesta == si.

% Regla para obtener los exámenes de diagnóstico requeridos
diagnosticos_requeridos(Enfermedad, Examenes) :-
    findall(Examen, requiere_diagnostico(Enfermedad, Examen), Examenes).

% Reglas para determinar si un paciente necesita un tratamiento específico:
necesita_tratamiento(Paciente, Tratamiento) :-
    presenta_sintoma(Enfermedad, _),
    recibe_tratamiento(Enfermedad, Tratamiento),
    verify_tratamiento(Paciente, Tratamiento).

verify_tratamiento(_, Tratamiento) :-
    write('El paciente requiere tratamiento de '), 
    write(Tratamiento), nl,
    write('¿Está de acuerdo? (si/no) '), 
    read(Respuesta),
    Respuesta == si.

% Reglas para la prevención de enfermedades:
aplica_prevencion(Paciente, Medida) :-
    factor_riesgo(Factor),
    prevencion(Medida),
    causa(Factor, Enfermedad),
    presentan_sintomas(Enfermedad, Paciente).

presentan_sintomas(Enfermedad, _) :-
    presenta_sintoma(Enfermedad, _),
    write('El paciente presenta síntomas de '), 
    write(Enfermedad), nl.

% Reglas para determinar si un paciente necesita un diagnóstico específico:
necesita_diagnostico(Paciente, Diagnostico) :-
    presenta_sintoma(Enfermedad, _),
    requiere_diagnostico(Enfermedad, Diagnostico),
    verify_diagnostico(Paciente, Diagnostico).

verify_diagnostico(_, Diagnostico) :-
    write('Se requiere realizar el siguiente diagnóstico: '), 
    write(Diagnostico), nl,
    write('¿Está de acuerdo? (si/no) '), 
    read(Respuesta),
    Respuesta == si.
% Reglas para proporcionar información sobre los factores de riesgo:
info_factor_riesgo(Factor) :-
    causa(Factor, Enfermedad),
    write('El factor de riesgo '), 
    write(Factor),
    write(' puede causar '), 
    write(Enfermedad), nl.

% Reglas para identificar los síntomas específicos de una enfermedad:
sintomas_enfermedad(Enfermedad, Sintomas) :-
    findall(Sintoma, presenta_sintoma(Enfermedad, Sintoma), Sintomas).

% Reglas para proporcionar recomendaciones de estilo de vida para prevenir enfermedades:
recomendacion_prevencion(Enfermedad, Recomendacion) :-
    prevencion(Recomendacion),
    causa(Factor, Enfermedad),
    write('Para prevenir '), 
    write(Enfermedad),
    write(', se recomienda '), 
    write(Recomendacion), 
    write(' debido a la exposición a '), 
    write(Factor), nl.

% Reglas para identificar los tratamientos disponibles para una enfermedad
tratamientos_disponibles(Enfermedad, Tratamientos) :-
    findall(Tratamiento, recibe_tratamiento(Enfermedad, Tratamiento), Tratamientos).

% Reglas para determinar la efectividad de un tratamiento en un paciente:
efectividad_tratamiento(Paciente, Tratamiento) :-
    recibe_tratamiento(Enfermedad, Tratamiento),
    presenta_sintoma(Enfermedad, Sintoma),
    not(verificar_efectividad(Paciente, Sintoma)).

verificar_efectividad(_, Sintoma) :-
    write('¿El tratamiento ha mejorado el síntoma '), 
    write(Sintoma), 
    write(' en el paciente? (si/no) '), 
    read(Respuesta),
    Respuesta == si.

% Reglas para proporcionar información sobre los metodos de diagnóstico:
info_diagnostico(Enfermedad, Diagnosticos) :-
    findall(Diagnostico, requiere_diagnostico(Enfermedad, Diagnostico), Diagnosticos),
    write('Los siguientes exámenes son necesarios para diagnosticar '), 
    write(Enfermedad), 
    write(': '), 
    write(Diagnosticos), nl.

% Reglas para identificar pacientes en riesgo debido a multiples factores:
paciente_en_riesgo(Paciente, Enfermedad) :-
    presenta_sintoma(Enfermedad, _),
    factor_riesgo(Factor),
    causa(Factor, Enfermedad),
    presenta_sintoma(Factor, _),
    write('El paciente '), 
    write(Paciente), 
    write(' está en riesgo de '), 
    write(Enfermedad), 
    write(' debido a '), 
    write(Factor), nl.

% Reglas para identificar pacientes con multiples enfermedades:
paciente_con_enfermedades(Paciente, Enfermedades) :-
    presenta_sintoma(Enfermedad, _),
    causa(_, Enfermedad),
    Enfermedades \= Enfermedad,
    paciente_presenta_sintomas(Paciente, Enfermedades).

paciente_presenta_sintomas(Paciente, [Enfermedad|Resto]) :-
    presenta_sintoma(Enfermedad, _),
    paciente_presenta_sintomas(Paciente, Resto).
paciente_presenta_sintomas(_, []).

% Reglas para proporcionar recomendaciones específicas para cada factor de riesgo:
recomendacion_factor_riesgo(Factor, Recomendacion) :-
    factor_riesgo(Factor),
    prevencion(Recomendacion),
    write('Se recomienda '), 
    write(Recomendacion), 
    write(' para reducir el riesgo debido a '), 
    write(Factor), nl.

% Reglas para identificar pacientes con síntomas específicos:
paciente_con_sintomas(Paciente, _) :-
    presenta_sintoma(_, Sintoma),
    paciente_presenta_sintoma(Paciente, Sintoma).

paciente_presenta_sintoma(Paciente, Sintoma) :-
    write('El paciente '), 
    write(Paciente), 
    write(' presenta el síntoma '), 
    write(Sintoma), nl.

% Reglas para determinar si un paciente necesita seguimiento medico:
necesita_seguimiento(Paciente) :-
    presenta_sintoma(Enfermedad, _),
    causa(_, Enfermedad),
    paciente_con_sintomas(Paciente, _),
    write('El paciente '), 
    write(Paciente), 
    write(' necesita seguimiento médico debido a síntomas de una enfermedad relacionada.'), nl.

% Reglas para proporcionar información sobre la progresión de una enfermedad:
progresion_enfermedad(Enfermedad, Progresion) :-
    diagnostico(Prueba),
    requiere_diagnostico(Enfermedad, Prueba),
    progresion(Prueba, Progresion).

progresion(radiografia_torax, 'La radiografía de tórax puede mostrar la extensión del cáncer y si se ha diseminado a los ganglios linfáticos.').

% Reglas para identificar pacientes que han sido expuestos a multiples factores de riesgo:
paciente_expuesto_a_factores(Paciente, Factores) :-
    factor_riesgo(Factor),
    causa(Factor, _),
    presenta_sintoma(Factor, _),
    paciente_expuesto(Paciente, Factor),
    not(member(Factor, Factores)), % Evitar duplicados
    paciente_expuesto_a_factores(Paciente, [Factor|Factores]).

paciente_expuesto(Paciente, Factor) :-
    write('El paciente '), 
    write(Paciente), 
    write(' ha sido expuesto al factor de riesgo '), 
    write(Factor), nl.

% Reglas para identificar pacientes que requieren multiples tratamientos:
paciente_con_varios_tratamientos(Paciente, Tratamientos) :-
    recibe_tratamiento(_, Tratamiento),
    paciente_recibe_tratamiento(Paciente, Tratamiento),
    not(member(Tratamiento, Tratamientos)), % Evitar duplicados
    paciente_con_varios_tratamientos(Paciente, [Tratamiento|Tratamientos]).

paciente_recibe_tratamiento(Paciente, Tratamiento) :-
    write('El paciente '), 
    write(Paciente), 
    write(' recibe el tratamiento de '), 
    write(Tratamiento), nl.

% Reglas para proporcionar información sobre la duración del tratamiento:
duracion_tratamiento(Enfermedad, Tratamiento, Duracion) :-
    recibe_tratamiento(Enfermedad, Tratamiento),
    duracion_aproximada(Enfermedad, Tratamiento, Duracion).

duracion_aproximada(_, cirugia, 'La duración del tratamiento quirúrgico puede variar según la extensión y la etapa de la enfermedad.').

% Reglas para determinar la necesidad de seguimiento post-tratamiento:
necesita_seguimiento_post_tratamiento(Paciente) :-
    recibe_tratamiento(_, _),
    presenta_sintoma(_, _),
    write('El paciente '), 
    write(Paciente), 
    write(' necesita seguimiento médico después del tratamiento.'), nl.
% Reglas para proporcionar información sobre las complicaciones del tratamiento:
complicaciones_tratamiento(Tratamiento, Complicaciones) :-
    tratamiento(Tratamiento),
    complicaciones_conocidas(Tratamiento, Complicaciones).

complicaciones_conocidas(radioterapia, 'Algunas complicaciones comunes de la radioterapia incluyen fatiga, irritación cutánea y cambios en el área tratada.').

% Reglas para identificar pacientes que necesitan pruebas de seguimiento:
necesita_seguimiento(_, Pruebas) :-
    presenta_sintoma(Enfermedad, _),
    recibe_tratamiento(Enfermedad, _),
    pruebas_seguimiento(Enfermedad, Pruebas).

pruebas_seguimiento(Enfermedad, Pruebas) :-
    findall(Prueba, requiere_diagnostico(Enfermedad, Prueba), Pruebas),
    write('Se recomienda realizar las siguientes pruebas de seguimiento: '), 
    write(Pruebas), nl.
% Reglas para identificar pacientes que han completado su tratamiento:
tratamiento_completado(Paciente) :-
    not(presenta_sintoma(_, _)),
    write('El paciente '), 
    write(Paciente), 
    write(' ha completado su tratamiento y no presenta síntomas.'), nl.
% Reglas para proporcionar información sobre el pronóstico de una enfermedad:
pronostico_enfermedad(Enfermedad, Pronostico) :-
    pronostico_conocido(Enfermedad, Pronostico).

pronostico_conocido(cancer_pulmonar, 'El pronóstico del cáncer de pulmón depende de la etapa en la que se detecta y del tipo de tratamiento recibido.').

% Reglas para identificar pacientes que necesitan ajustes en el tratamiento:
necesita_ajuste_tratamiento(Paciente, Tratamiento) :-
    recibe_tratamiento(_, Tratamiento),
    presenta_sintoma(_, _),
    \+ (efectividad_tratamiento(Paciente, Tratamiento),
        write('¿El tratamiento ha mejorado el síntoma en el paciente? (si/no) '),
        read(Respuesta),
        Respuesta == no),
    write('El paciente '), 
    write(Paciente), 
    write(' necesita ajustes en el tratamiento de '), 
    write(Tratamiento), nl.
    
% Reglas para proporcionar información sobre la progresión de la enfermedad en un paciente:
progresion_enfermedad_paciente(Paciente, Enfermedad, Progresion) :-
    presenta_sintoma(Enfermedad, _),
    causa(_, Enfermedad),
    write('La enfermedad ha progresado en el paciente '), 
    write(Paciente), 
    write(' debido a '), 
    write(Enfermedad), 
    write(': '), 
    write(Progresion), nl.

% Reglas para identificar pacientes que necesitan recomendaciones de estilo de vida específicas:
recomendacion_estilo_vida(Paciente, Recomendacion) :-
    factor_riesgo(Factor),
    prevencion(Recomendacion),
    causa(Factor, Enfermedad),
    presenta_sintoma(Enfermedad, _),
    write('Se recomienda '), 
    write(Recomendacion), 
    write(' para el paciente '), 
    write(Paciente), 
    write(' debido a la exposición a '), 
    write(Factor), nl.

% Reglas para proporcionar información sobre efectos secundarios de tratamientos específicos:
efectos_secundarios_tratamiento(Tratamiento, Efectos) :-
    tratamiento(Tratamiento),
    efectos_secundarios_conocidos(Tratamiento, Efectos).

efectos_secundarios_conocidos(quimioterapia, 'Algunos efectos secundarios comunes de la quimioterapia incluyen náuseas, fatiga y pérdida de cabello.').

% Reglas para identificar pacientes que necesitan consejería psicológica:
necesita_consejeria_psicologica(Paciente) :-
    presenta_sintoma(Enfermedad, _),
    recibe_tratamiento(Enfermedad, _),
    factores_psicologicos(Enfermedad),
    write('El paciente '), 
    write(Paciente), 
    write(' puede necesitar consejería psicológica debido al tratamiento de su enfermedad.'), nl.

% Reglas para proporcionar recomendaciones de seguimiento post-tratamiento:
recomendacion_seguimiento_post_tratamiento(Enfermedad, Recomendacion) :-
    presentan_sintomas(Enfermedad, _),
    recibe_tratamiento(Enfermedad, _),
    seguimiento_post_tratamiento(Enfermedad, Recomendacion).

seguimiento_post_tratamiento(cancer_pulmonar, 'Se recomienda realizar exámenes de seguimiento periódicos, como radiografías de tórax, para monitorear la recurrencia del cáncer de pulmón.').

% Reglas para identificar pacientes con necesidades dietéticas específicas:
necesita_intervencion_dietetica(Paciente) :-
    presenta_sintoma(Enfermedad, _),
    recibe_tratamiento(Enfermedad, _),
    necesidad_dietetica(Enfermedad),
    write('El paciente '), 
    write(Paciente), 
    write(' puede necesitar intervención dietética como parte de su tratamiento médico.'), nl.
