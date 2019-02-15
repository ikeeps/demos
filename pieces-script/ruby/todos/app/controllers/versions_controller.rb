class VersionsController < ApplicationController
  def revert
    # @version = PaperTrail::Version.includes(:item).find(params[:id])
    @version = PaperTrail::Version.find(params[:id])
    if @version.reify
      @version.reify.save!
    else
      # puts @version.association(:item).loaded?
      # puts @version.association(:item).inspect
      @version.item.destroy
    end
    link_name = params[:redo] == true ? "undo" : "redo"
    link = view_context.link_to(link_name, revert_version_path(@version.next, :redo => !params[:redo]), method: :post)
    redirect_back fallback_location: '/', allow_other_host: false, notice: "Undid #{@version.event}. #{link}"
  end
end
