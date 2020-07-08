use DBKinal2019;

DELIMITER $$ 
create procedure sp_NuevoGrado( p_grado varchar(100), p_seccion varchar(100), p_modalidad varchar(100), p_jornada varchar(100))
begin 
	insert into Grado(grado,seccion,modalidad,jornada)
    values( p_grado, p_seccion, p_modalidad, p_jornada);
end $$ 
DELIMITER ;



DELIMITER $$ 
create procedure sp_EliminarGrado(p_codigoGrado int)
begin
	delete from Grado where (codigoGrado = p_codigoGrado);
end $$ 
DELIMITER ;

DELIMITER $$ 
create procedure sp_EditarGrado(p_codigoGrado int, p_grado varchar(100), p_seccion varchar(100), p_modalidad varchar(100), p_jornada varchar(100))
begin 
	update Grado 
    set grado = p_grado,
    seccion = p_seccion,
    modalidad = p_modalidad,
    jornada = p_jornada
    where codigoGrado = p_codigoGrado;
end $$ 
DELIMITER ;

DELIMITER $$
create function fn_NotaTotal(notaAcumulativa decimal(10,2), notaEvaluacion decimal(10,2)) 
	returns decimal(10,2)
reads sql data deterministic
BEGIN
	declare totalBimestre decimal(10,2);
    
	set totalBimestre = (notaEvaluacion*0.60) + (notaAcumulativa*0.40);
    
	return totalBimestre;
END $$
DELIMITER ;
update CalificacionBimestral set totalBimestre = (select fn_NotaTotal(notaAcumulativa, notaEvaluacion));

call sp_NuevoGrado;
call sp_eliminarGrado;
call sp_EditarGrado;

call fn_NotaTotal;