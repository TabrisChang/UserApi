package tw.com.firstbank.fcbcore.fir.service.application.in.core;

public interface UseCaseApi<T extends RequestCommand, K extends ResponseCommand> {

  K execute(T requestCommand);

}
