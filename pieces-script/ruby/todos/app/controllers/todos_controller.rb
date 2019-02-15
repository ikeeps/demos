class TodosController < ApplicationController
  def index
    @todos = Todo.all
    @todo = Todo.new
  end
  
  def new
    @todo = Todo.new
  end
  
  def create
    @todo = Todo.new(todo_params)
    
    if @todo.save 
      redirect_to todos_path, :notice => "Successfully created todo. #{undo_link}"
    else
      render 'new'
    end
  end
  
  def show
    @todo = Todo.find(params[:id])
  end
  
  def edit
    @todo = Todo.find(params[:id])
  end
  
  def update
    @todo = Todo.find(params[:id])
    if @todo.update(todo_params)
      redirect_to todos_path
    else
      render 'edit'
    end
  end
  
  def destroy
    @todo = Todo.find(params[:id])
    @todo.destroy
    redirect_to todos_path
  end
  
  private
  def todo_params
    params.require(:todo).permit(:subject, :aasm_state, :position)
  end
  
  def undo_link
    view_context.link_to("undo", revert_version_path(@todo.versions.last), :method => :post)
  end
end
